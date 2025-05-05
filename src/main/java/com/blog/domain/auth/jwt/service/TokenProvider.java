package com.blog.domain.auth.jwt.service;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.blog.domain.user.domain.entity.User;
import com.blog.domain.auth.jwt.exception.HmacSignatureException;
import com.blog.domain.auth.jwt.exception.InvalidTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TokenProvider {
	private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

	@Value("${blog.jwt.access.expiration}")
	private Long accessTokenExpirationPeriod;
	@Value("${blog.jwt.refresh.expiration}")
	private Long refreshTokenExpirationPeriod;
	@Value("${blog.jwt.key}")
	private String SECRET_KEY;
	private static final String HEADER_JSON = "{\"alg\":\"HS512\",\"typ\":\"JWT\"}";

	public String createToken(long id, String email, Long expirationPeriod){
		Date now = new Date();

		Map<String, Object> payloadMap = new HashMap<>();
		payloadMap.put("sub", email);
		payloadMap.put("id", id);
		payloadMap.put("exp", new Date(now.getTime() + expirationPeriod));

		String header = Base64.getUrlEncoder().withoutPadding().encodeToString(HEADER_JSON.getBytes());
		String payload;
		try {
			payload = Base64.getUrlEncoder().withoutPadding()
				.encodeToString(new ObjectMapper().writeValueAsBytes(payloadMap));
		}
		catch (Exception e){
			throw new InvalidTokenException();
		}

		// 서명 생성 (HMAC-SHA512)
		String signature = sign(header + "." + payload, SECRET_KEY);

		return header + "." + payload + "." + signature;
	}

	public static String sign(String data, String key) {
		try {
			Mac hmac = Mac.getInstance("HmacSHA512");
			hmac.init(new SecretKeySpec(key.getBytes(), "HmacSHA512"));
			return Base64.getUrlEncoder().withoutPadding().encodeToString(hmac.doFinal(data.getBytes()));
		}
		catch(Exception e){
			logger.error( "JWT 서명 생성 중 오류 발생", e);
			throw new HmacSignatureException();
		}
	}

	public String createAccessToken(User user) {
		return createToken(user.getId(), user.getEmail(), accessTokenExpirationPeriod);
	}

	public String createRefreshToken(User user) {
		return createToken(user.getId(), user.getEmail(), refreshTokenExpirationPeriod);
	}

}
