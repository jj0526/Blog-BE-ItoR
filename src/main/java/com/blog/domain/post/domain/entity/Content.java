package com.blog.domain.post.domain.entity;

import com.blog.domain.post.domain.entity.enums.ContentType;
import com.blog.global.common.entity.BaseEntity;


public class Content extends BaseEntity {

	private long id;
	private Post post;
	private String contentData;
	private ContentType contentType;

	public Content(Post post, String contentData, ContentType contentType) {
		this.post = post;
		this.contentData = contentData;
		this.contentType = contentType;
	}

	public Content(long id, Post post, String contentData, ContentType contentType) {
		this.id = id;
		this.post = post;
		this.contentData = contentData;
		this.contentType = contentType;
	}

	public long getId() {
		return id;
	}

	public Post getPost() {
		return post;
	}

	public String getContentData() {
		return contentData;
	}

	public ContentType getContentType() {
		return contentType;
	}


}
