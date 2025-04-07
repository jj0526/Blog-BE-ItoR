package com.blog.domain.post.application.exception;

import com.blog.global.common.exception.BusinessLogicException;

public class InvalidPageNumberException extends BusinessLogicException {
	public InvalidPageNumberException(){
		super(400, "올바르지 않은 페이지입니다");
	}
}
