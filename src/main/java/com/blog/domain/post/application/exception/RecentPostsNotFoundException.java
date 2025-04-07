package com.blog.domain.post.application.exception;

import com.blog.global.common.exception.EntityNotFoundException;

public class RecentPostsNotFoundException extends EntityNotFoundException {
	public RecentPostsNotFoundException() {
		super("최근 게시물이 없습니다.");
	}
}
