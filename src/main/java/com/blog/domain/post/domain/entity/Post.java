package com.blog.domain.post.domain.entity;

import java.util.ArrayList;
import java.util.List;
import com.blog.domain.user.domain.User;
import com.blog.global.common.entity.BaseEntity;

public class Post extends BaseEntity {

	private long id;
	private User user;
	private String title;
	private long commentCount = 0;
	private List<Content> contents = new ArrayList<>();

	public long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public String getTitle() {
		return title;
	}

	public long getCommentCount() {
		return commentCount;
	}

	public List<Content> getContents() {
		return contents;
	}

	public void setId(long id){
		this.id = id;
	}

	public Post(User user, String title) {
		this.user = user;
		this.title = title;
	}

	public Post(long id, User user, String title, long commentCount, List<Content> contents) {
		this.id = id;
		this.user = user;
		this.title = title;
		this.commentCount = commentCount;
		this.contents = contents;
	}


	public void setContents(List<Content> contents) {
		this.contents = contents;
	}

}
