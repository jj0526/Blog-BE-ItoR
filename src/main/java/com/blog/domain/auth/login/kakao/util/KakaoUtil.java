package com.blog.domain.auth.login.kakao.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import com.blog.domain.auth.login.kakao.dto.KakaoLoginDTO;
import com.blog.domain.auth.login.kakao.dto.KakaoProfile;
import com.blog.domain.auth.login.exception.KakaoParsingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class KakaoUtil {
	@Value("${blog.kakao.auth.client}")
	private String client;
	@Value("${blog.kakao.auth.redirect}")
	private String redirect;

	public KakaoLoginDTO requestToken(String accessCode) {
		RestClient restClient = RestClient.create();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", client);
		params.add("redirect_url", redirect);
		params.add("code", accessCode);

		String response = restClient.post()
			.uri("https://kauth.kakao.com/oauth/token")
			.headers(httpHeaders -> httpHeaders.addAll(headers))
			.body(params)
			.retrieve()
			.body(String.class);

		ObjectMapper objectMapper = new ObjectMapper();

		try {
			return objectMapper.readValue(response, KakaoLoginDTO.class);
		} catch (JsonProcessingException e) {
			throw new KakaoParsingException();
		}
	}

	public KakaoProfile requestProfile(KakaoLoginDTO oAuthToken) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();

		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		headers.add("Authorization", "Bearer " + oAuthToken.getAccessToken());

		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			ResponseEntity<String> response = restTemplate.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.GET,
				kakaoProfileRequest,
				String.class);

			return objectMapper.readValue(response.getBody(), KakaoProfile.class);
		} catch (JsonProcessingException e) {
			throw new KakaoParsingException();
		}
	}
}
