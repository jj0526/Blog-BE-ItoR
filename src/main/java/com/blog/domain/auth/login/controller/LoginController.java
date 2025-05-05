package com.blog.domain.auth.login.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.domain.auth.login.currentUser.CurrentUser;
import com.blog.domain.auth.login.kakao.dto.KakaoDTO;
import com.blog.domain.auth.login.kakao.service.KakaoLoginService;
import com.blog.domain.auth.login.standard.dto.AuthenticationResultDTO;
import com.blog.domain.auth.login.standard.dto.LoginRequestDTO;
import com.blog.domain.auth.login.standard.service.LoginService;
import com.blog.global.common.response.CommonResponse;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api")
public class LoginController {
	private final LoginService loginService;
	private final KakaoLoginService kakaoLoginService;

	public LoginController(LoginService loginService, KakaoLoginService kakaoLoginService) {
		this.loginService = loginService;
		this.kakaoLoginService = kakaoLoginService;
	}

	@GetMapping("/login")
	public CommonResponse<AuthenticationResultDTO> login(@RequestBody LoginRequestDTO request){
		AuthenticationResultDTO authResult = loginService.authenticate(request);

		return CommonResponse.createSuccess(authResult);
	}
	@GetMapping("/auth/kakao/login")
	public CommonResponse<AuthenticationResultDTO> kakaoLogin(@RequestParam("code") String accessCode,
		HttpServletResponse httpServletResponse){
		return CommonResponse.createSuccess(kakaoLoginService.oAuthLogin(accessCode, httpServletResponse));
	}

	@PatchMapping("/auth/kakao/extra-info")
	public CommonResponse<Void> kakaoExtraInfo(@CurrentUser long userId, @Valid @RequestBody KakaoDTO.ExtraSave dto){
		kakaoLoginService.saveKakaoExtraInfo(userId, dto);
		return CommonResponse.createSuccess();
	}

}
