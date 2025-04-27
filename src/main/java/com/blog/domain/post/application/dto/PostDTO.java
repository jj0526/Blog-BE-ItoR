package com.blog.domain.post.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PostDTO {

	public record Save(
		@NotBlank(message = "제목을 입력해주세요.") String title,
		@NotNull(message = "내용이 널이어서는 안됩니다.")
		@Size(min = 1, message = "하나 이상의 내용의 리스트가 필요합니다")
		@Valid List<ContentDTO.Save> contents
	){}

	public record Response(
		long id,
		String title,
		String nickname,
		long commentCount,
		List<ContentDTO.Response> contents,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
		// 추후 댓글 추가 예정
	){}

	public record ResponseAll(
		long id,
		String title,
		String nickname,
		long commentCount,
		List<ContentDTO.Response> contents,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
	){}
}
