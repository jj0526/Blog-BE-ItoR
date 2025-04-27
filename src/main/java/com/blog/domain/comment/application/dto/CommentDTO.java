package com.blog.domain.comment.application.dto;

import jakarta.validation.constraints.NotBlank;

public class CommentDTO {

	public record Save(
		@NotBlank(message = "내용을 입력해주세요.")
		String content,
		String imageUrl
	){}
}
