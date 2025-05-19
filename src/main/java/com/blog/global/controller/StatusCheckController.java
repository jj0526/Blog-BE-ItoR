package com.blog.global.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusCheckController {

	@GetMapping("/health-check")
	public ResponseEntity<String> checkHealthStatus() {

		return ResponseEntity.ok("OK");
	}
}
