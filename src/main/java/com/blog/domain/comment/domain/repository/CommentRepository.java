package com.blog.domain.comment.domain.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.blog.domain.comment.domain.entity.Comment;

@Repository
public class CommentRepository {
	private final JdbcTemplate jdbcTemplate;

	public CommentRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void save(Comment comment) {
		String sql = "INSERT INTO comment (user_id, post_id, content, image_url) VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(
			sql,
			comment.getUser().getId(),
			comment.getPost().getId(),
			comment.getContent(),
			comment.getImageUrl()
		);
	}

}
