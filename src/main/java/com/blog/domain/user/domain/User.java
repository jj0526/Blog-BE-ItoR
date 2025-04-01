package com.blog.domain.user.domain;

import java.time.LocalDate;

import com.blog.domain.user.dto.UserSaveDTO;
import com.blog.global.common.entity.BaseEntity;

public class User extends BaseEntity {

	Long id;
	String email;
	String password;
	String name;
	String nickname;
	LocalDate birthDate;
	String profileImageUrl;
	String introduction;

	String refreshToken;
	Long kakaoId;

	public User(String email, String password, String name) {
		this.email = email;
		this.password = password;
		this.name = name;
	}

	public User(Long userId, String email, String password, String name) {
		this.id = userId;
		this.email = email;
		this.password = password;
		this.name = name;
	}

	public User(String email, String password, String name, Long kakaoId) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.kakaoId = kakaoId;
	}


	public User (String email, String password, String name, String nickname,
		String introduction, LocalDate birthDate, String profileImageUrl, Long kakaoId) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.kakaoId = kakaoId;
		this.nickname = nickname;
		this.introduction = introduction;
		this.birthDate = birthDate;
		this.profileImageUrl = profileImageUrl;
	}

	public static User fromDTO(UserSaveDTO userSaveDTO) {
		return new User(
			userSaveDTO.getEmail(),
			userSaveDTO.getPassword(),
			userSaveDTO.getName(),
			userSaveDTO.getNickname(),
			userSaveDTO.getIntroduction(),
			userSaveDTO.getBirthDate(),
			userSaveDTO.getProfileImageUrl(),
			null  // kakaoId는 UserSaveDTO에 없으므로 null로 설정
		);
	}


	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
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

	public String getRefreshToken() {
		return refreshToken;
	}


	public Long getKakaoId(){ return kakaoId; }

}
