package com.sweech.app.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sweech.app.dto.PostDto;
import com.sweech.app.model.Post;
import com.sweech.app.payload.CreatePostRequest;
import com.sweech.app.service.PostService;
import com.sweech.app.util.AuthUtil;

@RestController
@RequestMapping(value = "/api/posts", produces = "application/json")
public class PostController {

	@Autowired
	private PostService postService;

	// POST /api/posts
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> createPost(@RequestBody CreatePostRequest request, Authentication authentication) {
		Long userId = AuthUtil.getUserId(authentication);

		if (request.getTitle() == null || request.getTitle().length() < 1 || request.getTitle().length() > 30) {
			return ResponseEntity.badRequest()
					.body(Map.of("error", "invalid_title", "message", "Title must be 1–30 characters."));
		}
		if (request.getContent() == null || request.getContent().length() < 1 || request.getContent().length() > 1000) {
			return ResponseEntity.badRequest()
					.body(Map.of("error", "invalid_content", "message", "Content must be 1–1000 characters."));
		}

		Post post = postService.createPost(userId, request.getTitle(), request.getContent());
		return ResponseEntity
				.ok(Map.of("id", post.getId(), "title", post.getTitle(), "createdAt", post.getCreatedAt()));
	}

	// GET /api/posts?page=1
	@GetMapping
	public ResponseEntity<?> listPosts(@RequestParam(defaultValue = "1") int page) {
		List<PostDto> posts = postService.listPosts(page);
		int totalPosts = postService.countPosts();

		Map<String, Object> response = new HashMap<>();
		response.put("posts", posts);
		response.put("total", totalPosts);
		return ResponseEntity.ok(response);
	}

	// GET /api/posts/{id}
	@GetMapping("/{id}")
	public ResponseEntity<?> getPostDetail(@PathVariable("id") Long postId) {
		try {
			PostDto dto = postService.getPostDetail(postId);
			return ResponseEntity.ok(dto);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(404).body(Map.of("error", "not_found", "message", e.getMessage()));
		}
	}
}
