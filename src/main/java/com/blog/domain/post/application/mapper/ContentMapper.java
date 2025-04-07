package com.blog.domain.post.application.mapper;

import org.springframework.stereotype.Component;
import com.blog.domain.post.application.dto.ContentDTO;
import com.blog.domain.post.domain.entity.Content;
import com.blog.domain.post.domain.entity.Post;

@Component
public class ContentMapper {
	public Content fromDTO(ContentDTO.Save dto, Post post){
		return new Content(post, dto.contentData(), dto.contentType());
	}

	public ContentDTO.Response toResponse(Content content){
		return new ContentDTO.Response(
			content.getId(),
			content.getContentData(),
			content.getContentType());
	}
}
