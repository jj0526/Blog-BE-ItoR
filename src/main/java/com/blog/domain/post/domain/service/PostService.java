package com.blog.domain.post.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.domain.post.application.dto.ContentDTO;
import com.blog.domain.post.application.dto.PostDTO;
import com.blog.domain.post.application.exception.PostNotFoundException;
import com.blog.domain.post.application.exception.UnauthorizedPostAccessException;
import com.blog.domain.post.application.mapper.ContentMapper;
import com.blog.domain.post.application.mapper.PostMapper;
import com.blog.domain.post.domain.entity.Post;
import com.blog.domain.post.domain.repository.ContentRepository;
import com.blog.domain.post.domain.repository.PostRepository;
import com.blog.domain.user.domain.User;
import com.blog.domain.user.exception.UserNotFoundException;
import com.blog.domain.user.repository.UserRepository;

@Service
public class PostService {

	private final PostRepository postRepository;
	private final ContentRepository contentRepository;
	private final UserRepository userRepository;
	private final PostMapper postMapper;
	private final ContentMapper contentMapper;
	private final ContentService contentService;

	public PostService(PostRepository postRepository, ContentRepository contentRepository,
		UserRepository userRepository, PostMapper postMapper,
		ContentMapper contentMapper, ContentService contentService) {
		this.postRepository = postRepository;
		this.contentRepository = contentRepository;
		this.userRepository = userRepository;
		this.postMapper = postMapper;
		this.contentMapper = contentMapper;
		this.contentService = contentService;
	}

	public PostDTO.Response savePost(PostDTO.Save dto, Long userId) {
		User user = userRepository.find(userId).orElseThrow(UserNotFoundException::new);

		Post post = postMapper.fromDTO(dto, user);
		post = postRepository.save(post);

		List<ContentDTO.Response> contentsResponse = contentService.saveContents(dto, post);

		return postMapper.toResponse(post, contentsResponse);
	}

	public PostDTO.Response findPost(long postId) {
		Post post = postRepository.findByPostId(postId)
			.orElseThrow(PostNotFoundException::new);
		List<ContentDTO.Response> contentsResponse = contentService.findContents(post);

		return postMapper.toResponse(post, contentsResponse);
	}

	@Transactional
	public void deletePost(long postId, long userId) {
		Post post = postRepository.findByPostId(postId)
			.orElseThrow(PostNotFoundException::new);

		if(post.getUser().getId()!=userId){
			throw new UnauthorizedPostAccessException();
		}

		contentService.deleteContents(post);
		postRepository.delete(post);
	}

}
