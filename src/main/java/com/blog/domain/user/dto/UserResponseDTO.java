package com.blog.domain.user.dto;

import java.time.LocalDate;

import com.blog.domain.user.domain.User;

public class UserResponseDTO {

	private long userId;
	private String name;
	private String email;
	private Long kakaoId;
	private String nickname;
	private LocalDate birthDate;
	private String profileImageUrl;
	private String introduction;

	public long getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public Long getKakaoId() {
		return kakaoId;
	}

	public String getNickname() {
		return nickname;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public String getIntroduction() {
		return introduction;
	}

	public UserResponseDTO(long userId, String email, String name, Long kakaoId,
		String nickname, LocalDate birthDate, String profileImageUrl, String introduction) {
		this.userId = userId;
		this.email = email;
		this.name = name;
		this.kakaoId = kakaoId;
		this.nickname = nickname;
		this.birthDate = birthDate;
		this.profileImageUrl = profileImageUrl;
		this.introduction = introduction;
	}

	public static UserResponseDTO from(User user){
		return new UserResponseDTO(
			user.getId(),
			user.getEmail(),
			user.getName(),
			user.getKakaoId(),
			user.getNickname(),
			user.getBirthDate(),
			user.getProfileImageUrl(),
			user.getIntroduction()
		);
	}
}
