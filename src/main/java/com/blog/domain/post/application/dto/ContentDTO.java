package com.blog.domain.post.application.dto;

import com.blog.domain.post.domain.entity.enums.ContentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ContentDTO {
	public record Save(
		@NotBlank(message = "내용을 입력해주세요.") String contentData,
		@NotNull(message = "contentType은 null일 수 없으며, 'BODY' 또는 'IMAGE'여야 합니다.")
		ContentType contentType
	){}

	public record Response(
		long id,
		String contentData,
		ContentType contentType
	){}
}
