package com.blog.domain.post.domain.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.blog.domain.post.domain.entity.Content;

@Repository
public class ContentRepository {
	private final JdbcTemplate jdbcTemplate;

	public ContentRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void saveAll(List<Content> contents) {

		jdbcTemplate.batchUpdate(
			"INSERT INTO content (post_id, content_data, content_type) VALUES (?, ?, ?)",
			contents,
			contents.size(),
			(ps, content) -> {
				ps.setLong(1, content.getPost().getId());
				ps.setString(2, content.getContentData());
				ps.setString(3, content.getContentType().name());
			}
		);
	}
}
