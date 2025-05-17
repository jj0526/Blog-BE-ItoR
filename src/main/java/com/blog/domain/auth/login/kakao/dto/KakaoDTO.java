package com.blog.domain.auth.login.kakao.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public class KakaoDTO {
	public record ExtraSave(
		@NotBlank
		String nickname,
		@NotBlank
		String introduction,
		@NotNull
		@Past(message = "생일은 과거여야 합니다")
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		LocalDate birthDate,
		@NotBlank
		String profileImageUrl
	){}
}
