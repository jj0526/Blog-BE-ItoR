package com.blog.global.auth.login.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.global.auth.login.dto.AuthenticationResultDTO;
import com.blog.global.auth.login.dto.LoginRequestDTO;
import com.blog.global.auth.login.service.LoginService;
import com.blog.global.common.response.CommonResponse;

@RestController
@RequestMapping("/login")
public class LoginController {
	private final LoginService loginService;

	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}

	@PostMapping
	public CommonResponse<AuthenticationResultDTO> login(@RequestBody LoginRequestDTO request){
		AuthenticationResultDTO authResult = loginService.authenticate(request);

		return CommonResponse.createSuccess(authResult);
	}
}
