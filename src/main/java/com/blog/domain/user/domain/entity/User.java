package com.blog.domain.user.domain.entity;

import java.time.LocalDate;

import com.blog.global.common.entity.BaseEntity;

public class User extends BaseEntity {

	long id;
	String email;
	String password;
	String name;
	String nickname;
	LocalDate birthDate;
	String profileImageUrl;
	String introduction;

	String refreshToken;
	Long kakaoId;

	public User() {}

	public User(long id, String email, String name,
		Long kakaoId, String nickname, LocalDate birthDate, String profileImageUrl,
		String introduction) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.kakaoId = kakaoId;
		this.nickname = nickname;
		this.birthDate = birthDate;
		this.profileImageUrl = profileImageUrl;
		this.introduction = introduction;
	}

	public User( String password, String name, String nickname,
		LocalDate birthDate, String profileImageUrl,
		String introduction) {
		this.password = password;
		this.name = name;
		this.nickname = nickname;
		this.birthDate = birthDate;
		this.profileImageUrl = profileImageUrl;
		this.introduction = introduction;
	}

	public User(long id, String email, String password, String name) {
		this.id = id;
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



	public static User fromDTO(String email, String password, String name, String nickname,
		String introduction, LocalDate birthDate, String profileImageUrl, Long kakaoId) {

		return new User(email, password, name, nickname, introduction, birthDate, profileImageUrl, kakaoId);
	}

	public long getId() {
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


	public Long getKakaoId(){
		return kakaoId;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
}
