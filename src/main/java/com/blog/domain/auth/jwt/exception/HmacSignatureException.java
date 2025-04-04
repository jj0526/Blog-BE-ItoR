package com.blog.domain.auth.jwt.exception;

import com.blog.global.common.exception.BusinessLogicException;

public class HmacSignatureException extends BusinessLogicException {
	public HmacSignatureException() {
		super(400, "JWT 서명 생성 중 오류가 발생했습니다.");
	}
}
