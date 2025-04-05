package com.blog.domain.auth.login.standard.dto;

public class AuthenticationResultDTO {
	private String email;
	private long userId;
	private String accessToken;
	private String refreshToken;

	public AuthenticationResultDTO(long userId, String email, String accessToken, String refreshToken) {
		this.userId = userId;
		this.email = email;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public String getEmail() {
		return email;
	}

	public long getUserId() {
		return userId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

}
