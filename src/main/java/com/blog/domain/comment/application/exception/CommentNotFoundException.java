package com.blog.domain.comment.application.exception;

import com.blog.global.common.exception.EntityNotFoundException;

public class CommentNotFoundException extends EntityNotFoundException {
	public CommentNotFoundException() {
		super("존재하지 않는 댓글입니다.");
	}
}
