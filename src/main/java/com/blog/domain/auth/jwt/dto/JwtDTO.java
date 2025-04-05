package com.blog.domain.auth.jwt.dto;

public class JwtDTO {
	String accessToken;
	String refreshToken;

	public JwtDTO(String accessToken, String refreshToken){
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public String getAccessToken(){
		return accessToken;
	}

	public String getRefreshToken(){
		return refreshToken;
	}
}
