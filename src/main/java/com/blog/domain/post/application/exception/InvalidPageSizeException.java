package com.blog.domain.post.application.exception;

import com.blog.global.common.exception.BusinessLogicException;

public class InvalidPageSizeException extends BusinessLogicException {
	public InvalidPageSizeException(){
		super(400, "올바르지 않은 페이지 사이즈입니다");
	}
}
