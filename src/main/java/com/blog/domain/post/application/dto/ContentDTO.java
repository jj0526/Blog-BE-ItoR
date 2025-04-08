package com.blog.domain.post.application.dto;

import com.blog.domain.post.domain.entity.enums.ContentType;

public class ContentDTO {
	public record Save(
		String contentData,
		ContentType contentType
	){}

	public record Response(
		long id,
		String contentData,
		ContentType contentType
	){}
}
