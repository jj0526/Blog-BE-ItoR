package com.blog.domain.user.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.blog.domain.user.domain.User;

public class UserRowMapper implements RowMapper<User> {
	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new User(
			rs.getLong("ID"),
			rs.getString("EMAIL"),
			rs.getString("NAME"),
			rs.getObject("KAKAOID", Long.class),
			rs.getString("NICKNAME"),
			rs.getDate("BIRTHDATE") != null ? rs.getDate("BIRTHDATE").toLocalDate() : null,
			rs.getString("PROFILEIMAGEURL"),
			rs.getString("INTRODUCTION")
		);
	}

	public static User mapBasic(ResultSet rs) throws SQLException {
		return new User(
			rs.getLong("ID"),
			rs.getString("EMAIL"),
			rs.getString("PASSWORD"),
			rs.getString("NAME")
		);
	}

	public static User mapUpdateBasic(ResultSet rs) throws SQLException {
		return new User(
			rs.getString("PASSWORD"),
			rs.getString("NAME"),
			rs.getString("NICKNAME"),
			rs.getDate("BIRTHDATE").toLocalDate(),
			rs.getString("PROFILEIMAGEURL"),
			rs.getString("INTRODUCTION")
		);
	}
}
