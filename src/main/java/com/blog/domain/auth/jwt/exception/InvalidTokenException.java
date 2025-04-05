package com.blog.domain.auth.jwt.exception;

import com.blog.global.common.exception.BusinessLogicException;

public class InvalidTokenException extends BusinessLogicException {
	public InvalidTokenException() {
		super(400, "유효하지 않은 토큰입니다.");
	}
}
