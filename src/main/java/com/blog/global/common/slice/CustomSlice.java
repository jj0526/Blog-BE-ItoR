package com.blog.global.common.slice;

import java.util.List;

public class CustomSlice<T> {
	private List<T> content;
	private boolean hasNext;

	public CustomSlice(List<T> content, boolean hasNext) {
		this.content = content;
		this.hasNext = hasNext;
	}

	public List<T> getContent() {
		return content;
	}

	public boolean isHasNext() {
		return hasNext;
	}
}
