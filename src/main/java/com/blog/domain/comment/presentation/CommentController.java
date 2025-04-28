package com.blog.domain.comment.presentation;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blog.domain.auth.login.currentUser.CurrentUser;
import com.blog.domain.comment.application.dto.CommentDTO;
import com.blog.domain.comment.domain.service.CommentService;
import com.blog.global.common.response.CommonResponse;

@RestController
@RequestMapping("/api/posts")
public class CommentController {

	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping("/{postId}/comments")
	public CommonResponse<Void> saveComment(@PathVariable long postId, @CurrentUser long userId,
			@RequestBody CommentDTO.Save dto){
		commentService.save(postId, userId, dto);
		return CommonResponse.createSuccess();
	}

	@PatchMapping("/{postId}/comments/{commentId}")
	public CommonResponse<Void> updateComment(@PathVariable long postId, @PathVariable long commentId,
		@CurrentUser long userId, @RequestBody CommentDTO.Save dto){
		commentService.update(postId, userId, dto, commentId);
		return CommonResponse.createSuccess();
	}

	@DeleteMapping("/{postId}/comments/{commentId}")
	public CommonResponse<Void> deleteComment(@PathVariable long postId, @PathVariable long commentId,
		@CurrentUser long userId){
		commentService.delete(postId, userId, commentId);
		return CommonResponse.createSuccess();
	}

}
