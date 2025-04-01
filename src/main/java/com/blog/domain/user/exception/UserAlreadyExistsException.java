package com.blog.domain.user.exception;

import com.blog.global.common.exception.BusinessLogicException;

public class UserAlreadyExistsException extends BusinessLogicException {
	public UserAlreadyExistsException() {
		super(400, "이미 존재하는 유저입니다.");
	}
}
