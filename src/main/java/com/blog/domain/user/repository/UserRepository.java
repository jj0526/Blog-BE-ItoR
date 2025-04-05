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
			null
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
		String sql = "SELECT ID, REFRESHTOKEN, EMAIL, PASSWORD, NAME, KAKAOID, NICKNAME, BIRTHDATE, "
			+ "PROFILEIMAGEURL, INTRODUCTION, CREATEDAT, MODIFIEDAT FROM USER WHERE ID = ?";

		try {
			return Optional.of(jdbcTemplate.queryForObject(sql, userRowMapper, userId));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public Optional<User> findByRefreshToken(String refreshToken){
		String sql = "SELECT ID, EMAIL, PASSWORD, NAME FROM USER WHERE refreshToken = ?";

		try {
			return Optional.of(jdbcTemplate.queryForObject(sql, userRowMapper, refreshToken));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public Optional<User> findByEmail(String email) {
		String sql = "SELECT ID, EMAIL, PASSWORD, NAME FROM USER WHERE email = ?";

		try {
			return Optional.of(
				jdbcTemplate.queryForObject(sql, (rs, rowNum) -> UserRowMapper.mapBasic(rs), email)
			);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}
}
