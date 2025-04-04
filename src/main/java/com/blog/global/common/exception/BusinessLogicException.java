package com.blog.global.common.exception;

public abstract class BusinessLogicException extends RuntimeException {

	private final int statusCode;

	public BusinessLogicException(int code, String message) {
		super(message);
		this.statusCode = code;
	}
}
