package com.blog.domain.user.domain.service;

import org.springframework.stereotype.Service;

import com.blog.domain.user.application.dto.UserDTO;
import com.blog.domain.user.domain.entity.User;
import com.blog.domain.user.application.dto.UserResponseDTO;
import com.blog.domain.user.application.exception.UserAlreadyExistsException;
import com.blog.domain.user.application.exception.UserNotFoundException;
import com.blog.domain.user.domain.repository.UserRepository;
import com.blog.domain.auth.jwt.encoder.PasswordEncoder;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public void save(UserDTO.Save dto) {
		if(userRepository.findByEmail(dto.email()).isPresent()){
			throw new UserAlreadyExistsException();
		}
		User user = User.fromDTO(dto.email(), passwordEncoder.encrypt(dto.email(), dto.password()),
			dto.name(),
			dto.nickname(),
			dto.introduction(),
			dto.birthDate(),
			dto.profileImageUrl(),
			0L);	// kakaoId는 일반 로그인 유저가 사용하지 않으므로 0으로 설정);

		userRepository.save(user);
	}

	public UserResponseDTO getMyInfo(long userId) {
		User user = userRepository.find(userId).orElseThrow(UserNotFoundException::new);
		return UserResponseDTO.from(user);
	}
}
