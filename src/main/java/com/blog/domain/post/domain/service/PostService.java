package com.blog.domain.post.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.blog.domain.post.application.dto.ContentDTO;
import com.blog.domain.post.application.dto.PostDTO;
import com.blog.domain.post.application.exception.InvalidPageNumberException;
import com.blog.domain.post.application.exception.InvalidPageSizeException;
import com.blog.domain.post.application.exception.PostNotFoundException;
import com.blog.domain.post.application.exception.RecentPostsNotFoundException;
import com.blog.domain.post.application.exception.UnauthorizedPostAccessException;
import com.blog.domain.post.application.mapper.PostMapper;
import com.blog.domain.post.domain.entity.Post;
import com.blog.domain.post.domain.repository.PostRepository;
import com.blog.domain.user.domain.User;
import com.blog.domain.user.exception.UserNotFoundException;
import com.blog.domain.user.repository.UserRepository;
import com.blog.global.common.slice.CustomSlice;

@Service
public class PostService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final PostMapper postMapper;
	private final ContentService contentService;

	public PostService(PostRepository postRepository, UserRepository userRepository,
		PostMapper postMapper, ContentService contentService) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
		this.postMapper = postMapper;
		this.contentService = contentService;
	}

	@Transactional
	public void savePost(PostDTO.Save dto, Long userId) {
		User user = userRepository.find(userId).orElseThrow(UserNotFoundException::new);

		Post post = postMapper.fromDTO(dto, user);
		post = postRepository.save(post);

		contentService.saveContents(dto, post);

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

		validatePostAuthor(post, userId);

		contentService.deleteContents(post);
		postRepository.delete(post);
	}

	@Transactional
	public void updatePost(long postId, long userId, PostDTO.Save dto) {
		Post post = postRepository.findByPostId(postId)
			.orElseThrow(PostNotFoundException::new);

		validatePostAuthor(post, userId);

		postRepository.update(post, dto.title());

		contentService.deleteContents(post);
		contentService.saveContents(dto, post);
	}

	public CustomSlice<PostDTO.ResponseAll> findRecentPosts(int pageNumber, int pageSize) {
		validatePageRequest(pageNumber, pageSize);

		List<Post> recentPosts = postRepository.findRecentPosts(pageNumber, pageSize);
		if(recentPosts.isEmpty()){
			throw new RecentPostsNotFoundException();
		}

		boolean hasNext = recentPosts.size() > pageSize;
		if (hasNext) {
			recentPosts = recentPosts.subList(0, pageSize); // 마지막 post 없애기
		}

		List<PostDTO.ResponseAll> responses = recentPosts.stream()
			.map(post -> postMapper.toResponseAll(post, contentService.findContents(post)))
			.toList();

		return new CustomSlice<>(responses, hasNext);
	}

	private void validatePostAuthor(Post post, long userId){
		if(post.getUser().getId()!=userId){
			throw new UnauthorizedPostAccessException();
		}
	}

	private void validatePageRequest(int pageNumber, int pageSize) {
		if (pageNumber < 0) throw new InvalidPageNumberException();
		if (pageSize <= 0) throw new InvalidPageSizeException();
	}
}
