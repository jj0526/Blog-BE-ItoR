package com.blog.domain.user.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.domain.user.application.dto.UserDTO;
import com.blog.domain.user.domain.service.UserService;
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
	public CommonResponse<Void> signup(@Valid @RequestBody UserDTO.Save dto){
		userService.save(dto);
		return CommonResponse.createSuccess();
	}

	@GetMapping("/myinfo")
	public CommonResponse<UserDTO.Response> myInfo(@CurrentUser long userId){
		return CommonResponse.createSuccess(userService.getMyInfo(userId));
	}

	@PatchMapping()
	public CommonResponse<Void> updateInfo(@CurrentUser long userId, @RequestBody UserDTO.Update dto){
		userService.update(userId, dto);
		return CommonResponse.createSuccess();
	}
}
