package com.blog.domain.user.application.mapper;

import org.springframework.stereotype.Component;
import com.blog.domain.auth.jwt.encoder.PasswordEncoder;
import com.blog.domain.user.application.dto.UserDTO;
import com.blog.domain.user.domain.entity.User;

@Component
public class UserMapper {
	public User fromDTO(UserDTO.Save dto, PasswordEncoder passwordEncoder){
		return new User(dto.email(), passwordEncoder.encrypt(dto.email(), dto.password()),
			dto.name(),
			dto.nickname(),
			dto.introduction(),
			dto.birthDate(),
			dto.profileImageUrl(),
			0L);
	}

	public UserDTO.Response toResponse(User user){
		return new UserDTO.Response(user.getId(),
			user.getName(),
			user.getEmail(),
			user.getKakaoId(),
			user.getNickname(),
			user.getBirthDate(),
			user.getProfileImageUrl(),
			user.getIntroduction());
	}
}
