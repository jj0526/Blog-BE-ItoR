package com.blog.domain.auth.login.exception;

import com.blog.global.common.exception.BusinessLogicException;

public class KakaoParsingException extends BusinessLogicException {
	public KakaoParsingException() {
		super(400, "카카오 로그인 중 파싱에 실패하였습니다.");

	}
}
