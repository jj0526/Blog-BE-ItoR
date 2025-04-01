package com.blog.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.domain.user.domain.User;
import com.blog.domain.user.dto.UserSaveDTO;
import com.blog.domain.user.exception.UserAlreadyExistsException;
import com.blog.domain.user.repository.UserRepository;
import com.blog.global.auth.jwt.encoder.PasswordEncoder;

@Service
public class UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void save(UserSaveDTO userSaveDTO) {
		if(userRepository.findByEmail(userSaveDTO.getEmail()).isPresent()){
			throw new UserAlreadyExistsException();
		}
		User user = User.fromDTO(userSaveDTO.getEmail(),
			PasswordEncoder.encrypt(userSaveDTO.getEmail(), userSaveDTO.getPassword()),
			userSaveDTO.getName());

		userRepository.save(user);
	}

}
