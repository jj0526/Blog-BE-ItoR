package com.blog.domain.comment.application.mapper;

import org.springframework.stereotype.Component;
import com.blog.domain.comment.application.dto.CommentDTO;
import com.blog.domain.comment.domain.entity.Comment;
import com.blog.domain.post.domain.entity.Post;
import com.blog.domain.user.domain.entity.User;

@Component
public class CommentMapper {

	public Comment fromDTO(Post post, User user, CommentDTO.Save dto) {
		return new Comment(user, post, dto.content(), dto.imageUrl());
	}

	public CommentDTO.Response toResponse(Comment comment){
		return new CommentDTO.Response(comment.getId(), comment.getUser().getNickname(),
			comment.getUser().getProfileImageUrl(), comment.getImageUrl(), comment.getContent(), comment.getCreatedAt(), comment.getUpdatedAt());
	}
}
