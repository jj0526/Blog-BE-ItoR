package com.blog.domain.comment.domain.repository;

import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.blog.domain.comment.application.mapper.CommentRowMapper;
import com.blog.domain.comment.domain.entity.Comment;

@Repository
public class CommentRepository {
	private final JdbcTemplate jdbcTemplate;
	private final CommentRowMapper commentRowMapper;

	public CommentRepository(JdbcTemplate jdbcTemplate, CommentRowMapper commentRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.commentRowMapper = commentRowMapper;
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

	public Optional<Comment> findByCommentId(long commentId) {
		String sql = "SELECT id, user_id, post_id, content, image_url, created_at, updated_at FROM comment WHERE id = ?";
		try {
			return Optional.of(jdbcTemplate.queryForObject(sql, commentRowMapper, commentId));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	public void update(Comment comment, String content, String imageUrl) {
		String sql = "UPDATE comment SET content = ?, image_url = ? WHERE id = ?";
		jdbcTemplate.update(sql, content, imageUrl, comment.getId());
	}

	public void delete(Comment comment) {
		String sql = "DELETE FROM comment WHERE id = ?";
		jdbcTemplate.update(sql, comment.getId());
	}
}
