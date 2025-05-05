package com.blog.domain.comment.application.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public class CommentDTO {

	public record Save(
		@NotBlank(message = "내용을 입력해주세요.")
		String content,
		String imageUrl
	){}

	public record Response(
		long id,
		String nickname,
		String profileImageUrl,
		String imageUrl,
		String content,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
	){}
}
