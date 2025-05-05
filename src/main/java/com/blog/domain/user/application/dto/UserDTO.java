package com.blog.domain.user.application.dto;

import java.time.LocalDate;

import com.blog.domain.user.domain.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public class UserDTO {

	public record Save(
		@NotBlank String name,
		@Email String email,
		@NotBlank String password,
		@NotBlank String nickname,
		@NotNull()
		@Past(message = "생일은 과거여야 합니다")
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		LocalDate birthDate,
		String profileImageUrl,
		@NotBlank
		String introduction
	){};


	public record Response(
		long userId,
		String name,
		String email,
		Long kakaoId,
		String nickname,
		LocalDate birthDate,
		String profileImageUrl,
		String introduction
	){};

	public record Update(
		String password,
		String nickname,
		String profileImageUrl,
		String introduction
	){};

}
