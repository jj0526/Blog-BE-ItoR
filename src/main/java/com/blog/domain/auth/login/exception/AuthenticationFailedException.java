package com.blog.domain.auth.login.exception;

import com.blog.global.common.exception.BusinessLogicException;

public class AuthenticationFailedException extends BusinessLogicException {
	public AuthenticationFailedException() {
		super(400, "이메일 또는 비밀번호가 일치하지 않습니다.");

	}
}
