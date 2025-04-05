package com.blog.domain.user.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.blog.domain.user.domain.User;

@Repository
public class UserRepository {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public UserRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void update(User user) {
		jdbcTemplate.update(
			"update USER set EMAIL = ?, PASSWORD = ? where name = ?",
			user.getEmail(), user.getPassword(), user.getName());
	}

	public void updateRefreshToken(User user, String refreshToken) {
		jdbcTemplate.update(
			"update USER set REFRESHTOKEN = ? where ID = ?",
			refreshToken, user.getId());
	}

	public void save(User user) {
		jdbcTemplate.update(
			"INSERT INTO USER (email, password, name, nickname, birthDate, profileImageUrl, introduction, kakaoId) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
			user.getEmail(),
			user.getPassword(),
			user.getName(),
			user.getNickname(),
			user.getBirthDate(),
			user.getProfileImageUrl(),
			user.getIntroduction(),
			user.getKakaoId()
		);

	}

	public void kakaoSave(User user) {
		jdbcTemplate.update(
			"INSERT INTO USER (EMAIL, PASSWORD, NAME, KAKAOID, INTRODUCTION) VALUES (?, ?, ?, ?, ?)",
			user.getEmail(),
			user.getPassword(),
			user.getName(),
			user.getKakaoId(),
			"Write an introduction");
	}

	public Optional<User> find(long userId){
		String sql = "SELECT ID, EMAIL, NAME FROM USER WHERE ID = ?";

		try {
			return Optional.of(jdbcTemplate.queryForObject(
				sql,
				new Object[] {userId},
				(rs, rowNum) -> new User(
					rs.getLong("ID"),
					rs.getString("EMAIL"),
					rs.getString("NAME")
				)
			));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public Optional<User> findByRefreshToken(String refreshToken){
		String sql = "SELECT ID, EMAIL, NAME FROM USER WHERE refreshToken = ?";

		try {
			return Optional.of(jdbcTemplate.queryForObject(
				sql,
				new Object[] {refreshToken},
				(rs, rowNum) -> new User(
					rs.getLong("ID"),
					rs.getString("EMAIL"),
					rs.getString("NAME")
				)
			));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public Optional<User> findByEmail(String email) {
		String sql = "SELECT ID, EMAIL, NAME FROM USER WHERE email = ?";

		try {
			return Optional.of(jdbcTemplate.queryForObject(
				sql,
				new Object[] {email},
				(rs, rowNum) -> new User(
					rs.getLong("ID"),
					rs.getString("EMAIL"),
					rs.getString("NAME")
				)
			));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}
}
