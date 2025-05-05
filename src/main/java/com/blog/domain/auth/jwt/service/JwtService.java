package com.blog.domain.auth.jwt.service;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.blog.domain.auth.jwt.dto.JwtDTO;
import com.blog.domain.auth.jwt.exception.InvalidTokenException;
import com.blog.domain.auth.jwt.filter.JwtFilter;
import com.blog.domain.user.domain.entity.User;
import com.blog.domain.user.domain.repository.UserRepository;
import com.blog.global.common.response.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtService {

	private static final String BEARER = "Bearer ";

	private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

	@Value("${blog.jwt.access.header}")
	private String accessHeader;
	@Value("${blog.jwt.refresh.header}")
	private String refreshHeader;

	@Value("${blog.jwt.key}")
	private String SECRET_KEY;

	private final UserRepository userRepository;
	private final TokenProvider tokenProvider;

	public JwtService(UserRepository userRepository, TokenProvider tokenProvider) {
		this.userRepository = userRepository;
		this.tokenProvider = tokenProvider;
	}

	public Optional<String> extractRefreshToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(refreshHeader))
			.filter(refreshToken -> refreshToken.startsWith(BEARER))
			.map(refreshToken -> refreshToken.replaceFirst(BEARER, ""));
	}

	public Optional<String> extractAccessToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(accessHeader))
			.filter(refreshToken -> refreshToken.startsWith(BEARER))
			.map(refreshToken -> refreshToken.replaceFirst(BEARER, ""));
	}

	public long extractId(String token) {
		try {
			String[] parts = token.split("\\.");
			if (parts.length != 3) {
				throw new InvalidTokenException();
			}

			String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
			return extractIdFromPayload(payloadJson);
		} catch (Exception e) {
			logger.error("유효하지 않은 토큰입니다. {}", e);
			return -1L;
		}
	}

	private long extractIdFromPayload(String payloadJson){
		for (String entry : payloadJson.replace("{", "").replace("}", "").split(",")) {
			String[] keyValue = entry.split(":");
			if (keyValue.length == 2 && "id".equals(keyValue[0].trim().replace("\"", ""))) {
				return Long.parseLong(keyValue[1].trim().replace("\"", ""));
			}
		}
		return -1;
	}

	public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) throws
		IOException {
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		String message = new ObjectMapper().writeValueAsString(
			CommonResponse.createSuccess(new JwtDTO(accessToken, refreshToken)));
		response.getWriter().write(message);
	}

	public void checkAccessTokenAndRefreshToken(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain, String refreshToken) throws ServletException, IOException {

		String accessToken = extractAccessToken(request)
			.filter(this::isTokenValid)
			.orElse(null);

		if (accessToken == null) {
			checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
		} else {
			filterChain.doFilter(request, response);
		}

	}

	public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
		userRepository.findByRefreshToken(refreshToken)
			.ifPresent(user -> {
				String reIssuedRefreshToken = reIssueRefreshToken(user);
				String accessToken = tokenProvider.createAccessToken(user);
				try {
					sendAccessAndRefreshToken(response, accessToken, reIssuedRefreshToken);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			});
	}

	public boolean isTokenValid(String token) {
		try {
			String[] parts = token.split("\\.");
			if (parts.length != 3) {
				throw new InvalidTokenException();
			}

			String header = parts[0];
			String payload = parts[1];
			String providedSignature = parts[2];

			String data = header + "." + payload;

			String calculatedSignature = TokenProvider.sign(data, SECRET_KEY);

			return calculatedSignature.equals(providedSignature);
		} catch (Exception e) {
			logger.error("유효하지 않은 토큰입니다. {}", e);
			throw new InvalidTokenException();
		}
	}
	private String reIssueRefreshToken(User user) {
		String reIssuedRefreshToken = tokenProvider.createRefreshToken(user);
		userRepository.updateRefreshToken(user, reIssuedRefreshToken);
		return reIssuedRefreshToken;
	}

}
