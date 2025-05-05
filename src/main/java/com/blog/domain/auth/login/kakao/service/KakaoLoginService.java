package com.blog.domain.auth.login.kakao.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.domain.auth.login.kakao.dto.KakaoDTO;
import com.blog.domain.user.application.exception.UserNotFoundException;
import com.blog.domain.user.domain.repository.UserRepository;
import com.blog.domain.auth.jwt.service.TokenProvider;
import com.blog.domain.auth.login.kakao.dto.KakaoLoginDTO;
import com.blog.domain.auth.login.kakao.dto.KakaoProfile;
import com.blog.domain.auth.login.kakao.util.KakaoUtil;
import com.blog.domain.user.domain.entity.User;
import com.blog.domain.auth.login.standard.dto.AuthenticationResultDTO;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class KakaoLoginService {
	private final KakaoUtil kakaoUtil;
	private final UserRepository userRepository;
	private final TokenProvider tokenProvider;
	private static final Logger logger = LoggerFactory.getLogger(KakaoLoginService.class);

	public KakaoLoginService(KakaoUtil kakaoUtil, UserRepository userRepository, TokenProvider tokenProvider) {
		this.kakaoUtil = kakaoUtil;
		this.userRepository = userRepository;
		this.tokenProvider = tokenProvider;
	}

	@Transactional
	public AuthenticationResultDTO oAuthLogin(String accessCode, HttpServletResponse httpServletResponse) {
		KakaoLoginDTO kakaoLoginDTO = kakaoUtil.requestToken(accessCode);
		KakaoProfile kakaoProfile = kakaoUtil.requestProfile(kakaoLoginDTO);

		String email = kakaoProfile.getKakaoAccount().getEmail();

		User user = userRepository.findByEmail(email)
			.orElseGet(() -> saveNewKakaoUser(kakaoProfile));

		String accessToken = tokenProvider.createAccessToken(user);
		String refreshToken = tokenProvider.createRefreshToken(user);

		userRepository.updateRefreshToken(user, refreshToken);

		logger.info("email: {}", email);
		logger.info("accessToken: {}", accessToken);
		logger.info("refreshToken: {}", refreshToken);

		return new AuthenticationResultDTO(user.getId(), user.getEmail(), accessToken, refreshToken);
	}

	@Transactional
	public User saveNewKakaoUser(KakaoProfile kakaoProfile) {
		User kakaoUser = new User(
			kakaoProfile.getKakaoAccount().getEmail(),
			null,
			kakaoProfile.getKakaoAccount().getProfile().getNickname(),
			kakaoProfile.getId()
		);
		// Todo : 추후 카카오 로그인 유저 가입시 추가 정보도 받도록 수정
		userRepository.kakaoSave(kakaoUser);
		return userRepository.findByEmail(kakaoUser.getEmail()).orElseThrow(UserNotFoundException::new);
	}

	@Transactional
	public void saveKakaoExtraInfo(long userId, KakaoDTO.ExtraSave dto) {
		User user = userRepository.find(userId).orElseThrow(UserNotFoundException::new);

		userRepository.kakaoExtraInfoSave(user, dto);
	}
}
