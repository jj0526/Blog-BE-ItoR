package com.blog.domain.auth.jwt.filter;

import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.blog.domain.auth.jwt.exception.InvalidTokenException;
import com.blog.domain.auth.jwt.service.JwtService;
import com.blog.domain.auth.jwt.service.TokenProvider;
import com.blog.domain.user.domain.User;
import com.blog.domain.user.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
	private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
	@Value("#{'${blog.excluded-urls}'.split(',')}")
	private List<String> excludedUrls;
	@Value("${blog.jwt.key}")
	private String SECRET_KEY;


	private final TokenProvider tokenProvider;
	private final JwtService jwtService;
	private final UserRepository userRepository;

	@Autowired
	public JwtFilter(TokenProvider tokenProvider, JwtService jwtService, UserRepository userRepository) {
		this.tokenProvider = tokenProvider;
		this.jwtService = jwtService;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if (excludedUrls.contains(request.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}

		String accessToken = jwtService.extractAccessToken(request)
			.filter(this::isTokenValid)
			.orElse(null);	// 먼저 Access Token 추출

		if (accessToken != null) {// Access Token이 유효
			filterChain.doFilter(request, response);
			return;
		}

		String refreshToken = jwtService.extractRefreshToken(request)
			.filter(this::isTokenValid)
			.orElse(null);

		if (refreshToken != null) {
			checkAccessTokenAndRefreshToken(request, response, filterChain, refreshToken);
		}
	}

	public void checkAccessTokenAndRefreshToken(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain, String refreshToken) throws ServletException, IOException {

		String accessToken = jwtService.extractAccessToken(request)
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
					jwtService.sendAccessAndRefreshToken(response, accessToken, reIssuedRefreshToken);
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
