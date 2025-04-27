package com.blog.domain.post.application.exception;

import com.blog.global.common.exception.EntityNotFoundException;

public class PostNotFoundException extends EntityNotFoundException {
	public PostNotFoundException() {
		super("존재하지 않는 게시글입니다.");
	}
}
