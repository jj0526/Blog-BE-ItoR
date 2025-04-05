package com.blog.domain.auth.login.standard.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.blog.domain.auth.jwt.service.TokenProvider;
import com.blog.domain.user.domain.User;
import com.blog.domain.user.repository.UserRepository;
import com.blog.domain.auth.jwt.encoder.PasswordEncoder;
import com.blog.domain.auth.login.standard.dto.AuthenticationResultDTO;
import com.blog.domain.auth.login.standard.dto.LoginRequestDTO;
import com.blog.domain.auth.login.exception.AuthenticationFailedException;

@Service
public class LoginService {
	private final TokenProvider tokenProvider;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

	public LoginService(UserRepository userRepository, TokenProvider tokenProvider, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.tokenProvider = tokenProvider;
		this.passwordEncoder = passwordEncoder;
	}

	public AuthenticationResultDTO authenticate(LoginRequestDTO loginRequestDTO) {

		String email = loginRequestDTO.getEmail();
		String password = passwordEncoder.encrypt(email, loginRequestDTO.getPassword());

		User user = userRepository.findByEmail(email).orElseThrow(AuthenticationFailedException::new);
		if (!user.getPassword().equals(password)) {
			throw new AuthenticationFailedException();
		}

		// JWT 생성
		String accessToken = tokenProvider.createAccessToken(user);
		String refreshToken = tokenProvider.createRefreshToken(user);

		userRepository.updateRefreshToken(user, refreshToken);

		logger.info("email: {}", email);
		logger.info("accessToken: {}", accessToken);
		logger.info("refreshToken: {}", refreshToken);

		return new AuthenticationResultDTO(user.getId(), user.getEmail(), accessToken, refreshToken);
	}
}
