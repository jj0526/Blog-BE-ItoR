package com.blog.domain.comment.application.exception;

import com.blog.global.common.exception.BusinessLogicException;

public class UnauthorizedCommentAccessException extends BusinessLogicException {
	public UnauthorizedCommentAccessException(){
		super(400, "댓글에 대한 권한이 없습니다");
	}
}
