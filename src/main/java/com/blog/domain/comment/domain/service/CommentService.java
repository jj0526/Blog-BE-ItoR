package com.blog.domain.comment.domain.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.blog.domain.comment.application.dto.CommentDTO;
import com.blog.domain.comment.application.exception.CommentMismatchPostException;
import com.blog.domain.comment.application.exception.CommentNotFoundException;
import com.blog.domain.comment.application.exception.UnauthorizedCommentAccessException;
import com.blog.domain.comment.application.mapper.CommentMapper;
import com.blog.domain.comment.domain.entity.Comment;
import com.blog.domain.comment.domain.repository.CommentRepository;
import com.blog.domain.post.application.dto.PostDTO;
import com.blog.domain.post.application.exception.InvalidPageNumberException;
import com.blog.domain.post.application.exception.InvalidPageSizeException;
import com.blog.domain.post.domain.entity.Post;
import com.blog.domain.post.domain.service.PostService;
import com.blog.domain.user.domain.User;
import com.blog.domain.user.exception.UserNotFoundException;
import com.blog.domain.user.repository.UserRepository;
import com.blog.global.common.slice.CustomSlice;

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

	@Transactional
	public void save(long postId, long userId, CommentDTO.Save dto) {
		Post post = postService.getPost(postId);
		User user = userRepository.find(userId).orElseThrow(UserNotFoundException::new);

		Comment comment = commentMapper.fromDTO(post, user, dto);

		commentRepository.save(comment);
	}

	@Transactional
	public void update(long postId, long userId, CommentDTO.Save dto, long commentId) {

		Comment comment = commentRepository.findByCommentId(commentId)
			.orElseThrow(CommentNotFoundException::new);

		User user = userRepository.find(userId).orElseThrow(UserNotFoundException::new);

		validateCommentBelongsToPost(comment, postId);
		validateCommentAuthor(comment, user);

		commentRepository.update(comment, dto.content(), dto.imageUrl());
	}

	@Transactional
	public void delete(long postId, long userId, long commentId) {
		Comment comment = commentRepository.findByCommentId(commentId)
			.orElseThrow(CommentNotFoundException::new);

		User user = userRepository.find(userId).orElseThrow(UserNotFoundException::new);

		validateCommentBelongsToPost(comment, postId);
		validateCommentAuthor(comment, user);

		commentRepository.delete(comment);
	}

	public CustomSlice<CommentDTO.Response> findComments(long postId, int pageNumber, int pageSize){
		validatePageRequest(pageNumber, pageSize);
		postService.checkValidPostId(postId);

		List<Comment> comments = commentRepository.findCommentsByPostId(postId, pageNumber, pageSize);

		boolean hasNext = comments.size() > pageSize;
		if (hasNext) {
			comments = comments.subList(0, pageSize); // 마지막 comment 없애기
		}

		List<CommentDTO.Response> responses = comments.stream()
			.map(commentMapper::toResponse)
			.toList();

		return new CustomSlice<>(responses, hasNext);
	}

	private void validateCommentAuthor(Comment comment, User user){
		if(comment.getUser().getId() != user.getId()){
			throw new UnauthorizedCommentAccessException();
		}
	}

	private void validateCommentBelongsToPost(Comment comment, long postId){
		if (comment.getPost().getId() != postId) {
			throw new CommentMismatchPostException();
		}
	}

	private void validatePageRequest(int pageNumber, int pageSize) {
		if (pageNumber < 0) throw new InvalidPageNumberException();
		if (pageSize <= 0) throw new InvalidPageSizeException();
	}

	public long getCommentsCount(long postId){
		return commentRepository.countCommentsByPostId(postId);
	}
}
