package com.blog.global.common.response;

public class CommonResponse<T> {

	private int code;
	private String message;
	private T data;

	public int getCode(){
		return code;
	}
	public String getMessage(){
		return message;
	}
	public T getData(){
		return data;
	}

	public static <T> CommonResponse<T> createSuccess() {
		return createSuccess(null);
	}

	public static <T> CommonResponse<T> createSuccess(T data) {     // 성공
		return new CommonResponse<T>(200, "Success", data);
	}

	public static <T> CommonResponse<T> createFailure(int code, String message) {   // 실패
		return new CommonResponse<>(code, message, null);
	}

	public CommonResponse(int code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

}
