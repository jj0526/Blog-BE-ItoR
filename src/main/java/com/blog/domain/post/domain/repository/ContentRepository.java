package com.blog.domain.post.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.blog.domain.post.application.mapper.ContentRowMapper;
import com.blog.domain.post.domain.entity.Content;
import com.blog.domain.post.domain.entity.Post;

@Repository
public class ContentRepository {
	private final JdbcTemplate jdbcTemplate;
	private final ContentRowMapper contentRowMapper;

	public ContentRepository(JdbcTemplate jdbcTemplate, ContentRowMapper contentRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.contentRowMapper = contentRowMapper;
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

	public Optional<List<Content>> findByPost(Post post) {
		String sql = "SELECT id, post_id, content_data, content_type, created_at, updated_at FROM content WHERE post_id = ?";
		try {
			return Optional.of(jdbcTemplate.query(sql, contentRowMapper, post.getId()));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	public void deleteAll(List<Content> contentList) {
		String sql = "DELETE FROM content WHERE id = ?";
		for (Content content : contentList) {
			jdbcTemplate.update(sql, content.getId());
		}
	}
}
