package com.blog.domain.post.application.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.blog.domain.post.application.exception.PostNotFoundException;
import com.blog.domain.post.domain.entity.Content;
import com.blog.domain.post.domain.entity.enums.ContentType;
import com.blog.domain.post.domain.repository.PostRepository;

@Component
public class ContentRowMapper implements RowMapper<Content> {
	private final PostRepository postRepository;

	public ContentRowMapper(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@Override
	public Content mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Content(
			rs.getLong("id"),
			postRepository.findByPostId(rs.getLong("post_id")).orElseThrow(PostNotFoundException::new),
			rs.getString("content_data"),
			ContentType.valueOf(rs.getString("content_type"))
		);
	}
}
