package com.blog.domain.post.domain.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.blog.domain.post.domain.entity.Post;

@Repository
public class PostRepository {
	private final JdbcTemplate jdbcTemplate;

	public PostRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Post save(Post post) {
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(con -> {
			PreparedStatement ps = con.prepareStatement(
				"INSERT INTO post (user_id, title, comment_count) VALUES (?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS
			);
			ps.setLong(1, post.getUser().getId());
			ps.setString(2, post.getTitle());
			ps.setLong(3, post.getCommentCount());
			return ps;
		}, keyHolder);

		post.setId(keyHolder.getKey().longValue());
		return post;
	}
}
