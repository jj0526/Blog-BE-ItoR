package com.blog.domain.user.domain;

import java.util.Date;

import com.blog.domain.user.dto.UserSaveDTO;
import com.blog.global.common.entity.BaseEntity;

public class User extends BaseEntity {

	Long id;
	String email;
	String password;
	String name;
	String nickName;
	Date birthDate;
	String profileImageUrl;
	String introduction;

	String refreshToken;

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


	public static User fromDTO(String email, String password, String name) {
		return new User(email, password, name);
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

}
