package com.blog.domain.post.application.dto;

import java.util.List;

public class PostDTO {

	public record Save(
		String title,
		List<ContentDTO.Save> contents
	){}

	public record Response(
		Long id,
		String title,
		String nickname,
		long commentCount,
		List<ContentDTO.Response> contents
		// 추후 댓글 추가 예정
	){}

	public record ResponseAll(
		Long id,
		String title,
		String nickname,
		long commentCount,
		List<ContentDTO.Response> contents
	){}
}
