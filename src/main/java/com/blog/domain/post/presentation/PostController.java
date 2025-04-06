package com.blog.domain.post.presentation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.domain.auth.login.currentUser.CurrentUser;
import com.blog.domain.post.application.dto.PostDTO;
import com.blog.domain.post.domain.service.PostService;
import com.blog.domain.user.domain.User;
import com.blog.global.common.response.CommonResponse;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@PostMapping()
	public CommonResponse<Void> savePost(@RequestBody PostDTO.Save dto, @CurrentUser long userId){
		postService.savePost(dto, userId);
		return CommonResponse.createSuccess();
	}
}
