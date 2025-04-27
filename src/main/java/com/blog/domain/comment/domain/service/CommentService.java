package com.blog.domain.comment.domain.service;

import org.springframework.stereotype.Service;

import com.blog.domain.comment.application.dto.CommentDTO;
import com.blog.domain.comment.application.mapper.CommentMapper;
import com.blog.domain.comment.domain.entity.Comment;
import com.blog.domain.comment.domain.repository.CommentRepository;
import com.blog.domain.post.domain.entity.Post;
import com.blog.domain.post.domain.service.PostService;
import com.blog.domain.user.domain.User;
import com.blog.domain.user.exception.UserNotFoundException;
import com.blog.domain.user.repository.UserRepository;

@Service
public class CommentService
{
	private final CommentMapper commentMapper;
	private final CommentRepository commentRepository;
	private final PostService postService;
	private final UserRepository userRepository;

	public CommentService(CommentMapper commentMapper, CommentRepository commentRepository, PostService postService,
		UserRepository userRepository) {
		this.commentMapper = commentMapper;
		this.commentRepository = commentRepository;
		this.postService = postService;
		this.userRepository = userRepository;
	}

	public void save(long postId, long userId, CommentDTO.Save dto) {
		Post post = postService.getPost(postId);
		User user = userRepository.find(userId).orElseThrow(UserNotFoundException::new);

		Comment comment = commentMapper.fromDTO(post, user, dto);

		commentRepository.save(comment);
	}
}
