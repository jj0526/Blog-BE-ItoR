package com.blog.domain.user.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public class UserSaveDTO {

	@NotBlank String name;

	@Email String email;

	@NotBlank String password;

	@NotBlank String nickname ;

	@NotNull()
	@Past(message = "생일은 과거여야 합니다")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDate birthDate;

	private String profileImageUrl;

	@NotBlank private String introduction;

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


}
