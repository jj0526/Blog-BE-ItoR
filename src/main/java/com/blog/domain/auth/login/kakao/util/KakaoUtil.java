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

import com.blog.domain.auth.login.kakao.dto.KakaoDTO;
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

	public KakaoDTO requestToken(String accessCode) {
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
			return objectMapper.readValue(response, KakaoDTO.class);
		} catch (JsonProcessingException e) {
			throw new KakaoParsingException();
		}
	}

	public KakaoProfile requestProfile(KakaoDTO oAuthToken) {
		RestTemplate restTemplate2 = new RestTemplate();
		HttpHeaders headers2 = new HttpHeaders();

		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		headers2.add("Authorization", "Bearer " + oAuthToken.getAccessToken());

		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);

		ResponseEntity<String> response2 = restTemplate2.exchange(
			"https://kapi.kakao.com/v2/user/me",
			HttpMethod.GET,
			kakaoProfileRequest,
			String.class);

		KakaoProfile kakaoProfile = null;
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			kakaoProfile = objectMapper.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonProcessingException e) {
			throw new KakaoParsingException();
		}

		return kakaoProfile;
	}
}
