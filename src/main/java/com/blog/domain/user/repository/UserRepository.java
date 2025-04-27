package com.blog.domain.user.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.blog.domain.user.domain.User;
import com.blog.domain.user.mapper.UserRowMapper;

@Repository
public class UserRepository {
	private final JdbcTemplate jdbcTemplate;
	private final RowMapper<User> userRowMapper = new UserRowMapper();

	@Autowired
	public UserRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void update(User user) {
		jdbcTemplate.update(
			"UPDATE user SET email = ?, password = ? WHERE name = ?",
			user.getEmail(), user.getPassword(), user.getName());
	}

	public void updateRefreshToken(User user, String refreshToken) {
		jdbcTemplate.update(
			"UPDATE user SET refresh_token = ? WHERE id = ?",
			refreshToken, user.getId());
	}

	public void save(User user) {
		jdbcTemplate.update(
			"INSERT INTO user (email, password, name, nickname, birth_date, profile_image_url, introduction, kakao_id) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
			user.getEmail(),
			user.getPassword(),
			user.getName(),
			user.getNickname(),
			user.getBirthDate(),
			user.getProfileImageUrl(),
			user.getIntroduction(),
			null
		);

	}

	public void kakaoSave(User user) {
		jdbcTemplate.update(
			"INSERT INTO user (email, password, name, kakao_id, introduction) VALUES (?, ?, ?, ?, ?)",
			user.getEmail(),
			user.getPassword(),
			user.getName(),
			user.getKakaoId(),
			"Write an introduction");
	}

	public Optional<User> find(long userId){
		String sql = "SELECT id, refresh_token, email, password, name, kakao_id, nickname, birth_date, "
			+ "profile_image_url, introduction, created_at, updated_at FROM user WHERE id = ?";

		try {
			return Optional.of(jdbcTemplate.queryForObject(sql, userRowMapper, userId));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public Optional<User> findByRefreshToken(String refreshToken){
		String sql = "SELECT id, email, password, name FROM user WHERE refresh_token = ?";

		try {
			return Optional.of(jdbcTemplate.queryForObject(sql, userRowMapper, refreshToken));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public Optional<User> findByEmail(String email) {
		String sql = "SELECT id, email, password, name FROM user WHERE email = ?";

		try {
			return Optional.of(
				jdbcTemplate.queryForObject(sql, (rs, rowNum) -> UserRowMapper.mapBasic(rs), email)
			);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}
}
