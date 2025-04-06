package com.blog.domain.post.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blog.domain.post.application.dto.ContentDTO;
import com.blog.domain.post.application.dto.PostDTO;
import com.blog.domain.post.application.mapper.ContentMapper;
import com.blog.domain.post.domain.entity.Content;
import com.blog.domain.post.domain.entity.Post;
import com.blog.domain.post.domain.repository.ContentRepository;

@Service
public class ContentService
{
	private final ContentMapper contentMapper;
	private final ContentRepository contentRepository;

	public ContentService(ContentMapper contentMapper, ContentRepository contentRepository) {
		this.contentMapper = contentMapper;
		this.contentRepository = contentRepository;
	}

	public List<ContentDTO.Response> saveContents(PostDTO.Save dto, Post post){

		List<Content> contents = dto.contents().stream()
			.map(contentDto -> contentMapper.fromDTO(contentDto, post))
			.toList();

		contentRepository.saveAll(contents);
		post.setContents(contents);

		return contents.stream()
			.map(contentMapper::toResponse)
			.toList();
	}
}
