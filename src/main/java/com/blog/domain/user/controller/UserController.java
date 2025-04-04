package com.blog.domain.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.domain.user.dto.UserResponseDTO;
import com.blog.domain.user.dto.UserSaveDTO;
import com.blog.domain.user.service.UserService;
import com.blog.domain.auth.login.currentUser.CurrentUser;
import com.blog.global.common.response.CommonResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/signup")
	public CommonResponse<Void> signup(@Valid @RequestBody UserSaveDTO userSaveDTO){
		userService.save(userSaveDTO);
		return CommonResponse.createSuccess();
	}

	@GetMapping("/myinfo")
	public CommonResponse<UserResponseDTO> myInfo(@CurrentUser long userId){
		return CommonResponse.createSuccess(userService.getMyInfo(userId));
	}
}
