package com.blog.domain.post.application.exception;

import com.blog.global.common.exception.EntityNotFoundException;

public class ContentNotFoundException extends EntityNotFoundException {
	public ContentNotFoundException() {
		super("존재하지 않는 컨텐츠 입니다.");
	}
}
