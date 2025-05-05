package com.blog.global.common.exception;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.http.HttpStatus;

import com.blog.domain.user.application.exception.UserNotFoundException;

public enum ExceptionType {
	USER_NOT_FOUND(HttpStatus.BAD_REQUEST, UserNotFoundException.class);

	private final HttpStatus httpStatus;
	private final Class<? extends Exception> type;

	ExceptionType(HttpStatus httpStatus, Class<? extends Exception> type) {
		this.httpStatus = httpStatus;
		this.type = type;
	}

	public HttpStatus getHttpStatus(){
		return httpStatus;
	}

	public Class<? extends Exception> getType(){
		return type;
	}

	public static Optional<ExceptionType> findException(Exception ex) {
		return Arrays.stream(ExceptionType.values())
			.filter(e -> e.getType().equals(ex.getClass())) // 클래스 타입
			.findAny();
	}
}
