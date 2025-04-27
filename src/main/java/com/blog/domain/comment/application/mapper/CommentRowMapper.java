package com.blog.domain.comment.application.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.blog.domain.comment.domain.entity.Comment;
import com.blog.domain.post.application.exception.PostNotFoundException;
import com.blog.domain.post.domain.repository.PostRepository;
import com.blog.domain.user.exception.UserNotFoundException;
import com.blog.domain.user.repository.UserRepository;

@Component
public class CommentRowMapper implements RowMapper<Comment> {
	private final UserRepository userRepository;
	private final PostRepository postRepository;

	public CommentRowMapper(UserRepository userRepository, PostRepository postRepository) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}

	@Override
	public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
		Comment comment = new Comment(
			rs.getLong("id"),
			userRepository.find(rs.getLong("user_id"))
				.orElseThrow(UserNotFoundException::new),
			postRepository.findByPostId(rs.getLong("post_id"))
					.orElseThrow(PostNotFoundException::new),
			rs.getString("content"),
			rs.getString("image_url")
		);
		comment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
		comment.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

		return comment;
	}
}
