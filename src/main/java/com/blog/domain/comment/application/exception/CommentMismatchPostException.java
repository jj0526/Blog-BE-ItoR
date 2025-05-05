package com.blog.domain.comment.application.exception;

import com.blog.global.common.exception.BusinessLogicException;

public class CommentMismatchPostException extends BusinessLogicException {
	public CommentMismatchPostException(){
		super(400, "댓글이 지정된 게시글에 존재하지 않습니다.");
	}
}
