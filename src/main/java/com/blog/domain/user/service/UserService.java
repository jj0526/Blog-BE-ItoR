package com.blog.domain.user.service;

import org.springframework.stereotype.Service;
import com.blog.domain.user.domain.User;
import com.blog.domain.user.dto.UserResponseDTO;
import com.blog.domain.user.dto.UserSaveDTO;
import com.blog.domain.user.exception.UserAlreadyExistsException;
import com.blog.domain.user.exception.UserNotFoundException;
import com.blog.domain.user.repository.UserRepository;
import com.blog.domain.auth.jwt.encoder.PasswordEncoder;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public void save(UserSaveDTO userSaveDTO) {
		if(userRepository.findByEmail(userSaveDTO.getEmail()).isPresent()){
			throw new UserAlreadyExistsException();
		}
		User user = User.fromDTO(userSaveDTO.getEmail(), passwordEncoder.encrypt(userSaveDTO.getEmail(), userSaveDTO.getPassword()),
			userSaveDTO.getName(),
			userSaveDTO.getNickname(),
			userSaveDTO.getIntroduction(),
			userSaveDTO.getBirthDate(),
			userSaveDTO.getProfileImageUrl(),
			0L);	// kakaoId는 UserSaveDTO에 없으므로 0으로 설정);

		userRepository.save(user);
	}

	public UserResponseDTO getMyInfo(long userId) {
		User user = userRepository.find(userId).orElseThrow(UserNotFoundException::new);
		return UserResponseDTO.from(user);
	}
}
