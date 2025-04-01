package com.blog.global.auth.login.dto;

public class AuthenticationResultDTO {
	private String email;
	private Long userId;
	private String accessToken;
	private String refreshToken;

	public AuthenticationResultDTO( Long userId, String email, String accessToken, String refreshToken) {
		this.userId = userId;
		this.email = email;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public String getEmail() {
		return email;
	}

	public Long getUserId() {
		return userId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

}
