package com.blog.domain.auth.jwt.service;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.blog.domain.auth.jwt.dto.JwtDTO;
import com.blog.domain.auth.jwt.exception.InvalidTokenException;
import com.blog.global.common.response.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtService {

	private static final String BEARER = "Bearer ";

	private static final Logger logger = Logger.getLogger(JwtService.class.getName());

	@Value("${blog.jwt.access.header}")
	private String accessHeader;
	@Value("${blog.jwt.refresh.header}")
	private String refreshHeader;


	public Optional<String> extractRefreshToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(refreshHeader))
			.filter(refreshToken -> refreshToken.startsWith(BEARER))
			.map(refreshToken -> refreshToken.replace(BEARER, ""));
	}

	public Optional<String> extractAccessToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(accessHeader))
			.filter(refreshToken -> refreshToken.startsWith(BEARER))
			.map(refreshToken -> refreshToken.replace(BEARER, ""));
	}

	public Optional<Long> extractId(String token) {
		try {
			String[] parts = token.split("\\.");
			if (parts.length != 3) {
				throw new InvalidTokenException();
			}

			String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
			return extractIdFromPayload(payloadJson);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"유효하지 않은 토큰입니다. {}", e);
			return Optional.empty();
		}
	}

	private Optional<Long> extractIdFromPayload(String payloadJson){
		for (String entry : payloadJson.replace("{", "").replace("}", "").split(",")) {
			String[] keyValue = entry.split(":");
			if (keyValue.length == 2 && "id".equals(keyValue[0].trim().replace("\"", ""))) {
				return Optional.of(Long.parseLong(keyValue[1].trim().replace("\"", "")));
			}
		}
		return Optional.empty();
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

}
