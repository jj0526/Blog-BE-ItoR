package com.blog.domain.post.presentation;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blog.domain.auth.login.currentUser.CurrentUser;
import com.blog.domain.post.application.dto.PostDTO;
import com.blog.domain.post.domain.service.PostService;
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

	@GetMapping("/{postId}")
	public CommonResponse<PostDTO.Response> findPost(@PathVariable long postId){
		return CommonResponse.createSuccess(postService.findPost(postId));

	}

	@DeleteMapping("/{postId}")
	public CommonResponse<Void> deletePost(@PathVariable long postId, @CurrentUser long userId){
		postService.deletePost(postId, userId);
		return CommonResponse.createSuccess();
	}
}
