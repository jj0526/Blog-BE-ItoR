package com.blog.domain.post.domain.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.blog.domain.post.application.dto.ContentDTO;
import com.blog.domain.post.application.dto.PostDTO;
import com.blog.domain.post.application.exception.ContentNotFoundException;
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

	@Transactional
	public void saveContents(PostDTO.Save dto, Post post){

		List<Content> contents = dto.contents().stream()
			.map(contentDto -> contentMapper.fromDTO(contentDto, post))
			.toList();

		contentRepository.saveAll(contents);
	}

	public List<ContentDTO.Response> findContents(Post post) {
		List<Content> contentList = contentRepository.findByPost(post)
			.orElseThrow(ContentNotFoundException::new);
		return contentList.stream()
			.map(contentMapper::toResponse)
			.toList();
	}

	@Transactional
	public void deleteContents(Post post) {
		List<Content> contentList = contentRepository.findByPost(post)
			.orElseThrow(ContentNotFoundException::new);

		contentRepository.deleteAll(contentList);
	}
}
