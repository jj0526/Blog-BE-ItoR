package com.blog.domain.comment.domain.entity;

import com.blog.domain.post.domain.entity.Post;
import com.blog.domain.user.domain.User;
import com.blog.global.common.entity.BaseEntity;

public class Comment extends BaseEntity {

	private Long id;

	private User user;

	private Post post;

	private String content;

	private String imageUrl;

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public Post getPost() {
		return post;
	}

	public String getContent() {
		return content;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public Comment(long id, User user, Post post, String content, String imageUrl){
		this.id = id;
		this.user = user;
		this.post = post;
		this.content = content;
		this.imageUrl = imageUrl;
	}

	public Comment(User user, Post post, String content, String imageUrl){
		this.user = user;
		this.post = post;
		this.content = content;
		this.imageUrl = imageUrl;
	}

	public Comment(String content, String imageUrl){
		this.content = content;
		this.imageUrl = imageUrl;
	}

}
