package com.blog.domain.post.application.exception;

import com.blog.global.common.exception.BusinessLogicException;

public class UnauthorizedPostAccessException extends BusinessLogicException {
	public UnauthorizedPostAccessException(){
		super(400, "게시글에 대한 권한이 없습니다");
	}
}
