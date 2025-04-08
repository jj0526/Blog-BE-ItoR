package com.blog.domain.post.domain.entity.enums;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ContentType {
	BODY(),
	IMAGE();

	@JsonCreator
	public static ContentType parsing(String inputValue){
		if(inputValue == null){
			return null;
		}
		return Stream.of(ContentType.values())
			.filter(category ->
				category.toString().equals(inputValue.toUpperCase()))
			.findFirst()
			.orElse(null);
	}

}
