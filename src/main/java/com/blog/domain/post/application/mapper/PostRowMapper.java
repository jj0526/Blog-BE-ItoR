package com.blog.domain.post.application.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.blog.domain.post.domain.entity.Post;
import com.blog.domain.user.application.exception.UserNotFoundException;
import com.blog.domain.user.domain.repository.UserRepository;

@Component
public class PostRowMapper implements RowMapper<Post> {
	private final UserRepository userRepository;

	public PostRowMapper(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
		Post post = new Post(
			rs.getLong("id"),
			userRepository.find(rs.getLong("user_id"))
				.orElseThrow(UserNotFoundException::new),
			rs.getString("title"),
			rs.getLong("comment_count"),
			new ArrayList<>()
		);
		post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
		post.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

		return post;
	}
}
