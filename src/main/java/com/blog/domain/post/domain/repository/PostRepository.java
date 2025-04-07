package com.blog.domain.post.domain.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.blog.domain.post.application.dto.PostDTO;
import com.blog.domain.post.domain.entity.Post;

@Repository
public class PostRepository {
	private final JdbcTemplate jdbcTemplate;
	private final RowMapper<Post> postRowMapper;

	public PostRepository(JdbcTemplate jdbcTemplate, RowMapper<Post> postRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.postRowMapper = postRowMapper;
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

	public Optional<Post> findByPostId(long postId) {
		String sql = "SELECT id, user_id, title, comment_count, created_at, updated_at  FROM post WHERE id = ?";
		try {
			return Optional.of(jdbcTemplate.queryForObject(sql, postRowMapper, postId));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	public void delete(Post post) {
		String sql = "DELETE FROM post WHERE id = ?";
		jdbcTemplate.update(sql, post.getId());
	}

	public void update(Post post, String title) {
		String sql = "UPDATE post SET title = ? WHERE id = ?";
		jdbcTemplate.update(sql, title, post.getId());
	}

	public List<Post> findRecentPosts(int pageNumber, int pageSize) {
		String sql = "SELECT id, user_id, title, comment_count, created_at, updated_at FROM post ORDER BY id DESC LIMIT ? OFFSET ?";
		return jdbcTemplate.query(sql, postRowMapper, pageSize + 1, pageNumber * pageSize);
	}
}
