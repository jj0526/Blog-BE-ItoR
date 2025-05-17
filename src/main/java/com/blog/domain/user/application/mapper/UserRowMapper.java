package com.blog.domain.user.application.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.blog.domain.user.domain.entity.User;

public class UserRowMapper implements RowMapper<User> {
	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new User(
			rs.getLong("id"),
			rs.getString("email"),
			rs.getString("name"),
			rs.getObject("kakao_id", Long.class),
			rs.getString("nickname"),
			rs.getDate("birth_date") != null ? rs.getDate("birth_date").toLocalDate() : null,
			rs.getString("profile_image_url"),
			rs.getString("introduction")
		);
	}

	public static User mapBasic(ResultSet rs) throws SQLException {
		return new User(
			rs.getLong("id"),
			rs.getString("email"),
			rs.getString("password"),
			rs.getString("name")
		);
	}
}
