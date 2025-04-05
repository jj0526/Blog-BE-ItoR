package com.blog.domain.user.exception;

import com.blog.global.common.exception.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
	public UserNotFoundException() {
		super("존재하지 않는 유저입니다.");
	}
}
