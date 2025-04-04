package com.blog.domain.user.dto;

import com.blog.domain.user.domain.User;

public class UserResponseDTO {

	long userId;
	String name;
	String email;

	public long getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public UserResponseDTO(long userId, String name, String email){
		this.userId = userId;
		this.name = name;
		this.email = email;
	}

	public static UserResponseDTO from(User user){
		return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
	}
}
