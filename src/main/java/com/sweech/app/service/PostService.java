package com.sweech.app.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sweech.app.dto.PostDto;
import com.sweech.app.mapper.PostMapper;
import com.sweech.app.mapper.UserMapper;
import com.sweech.app.model.Post;
import com.sweech.app.model.User;

@Service
public class PostService {

	@Autowired
	private PostMapper postMapper;

	@Autowired
	private UserMapper userMapper;

	private static final int PAGE_SIZE = 20;

	public Post createPost(Long userId, String title, String content) {
		Post post = new Post();
		post.setUserId(userId);
		post.setTitle(title);
		post.setContent(content);
		post.setCreatedAt(Instant.now().toString());
		postMapper.insert(post);
		return post;
	}

	public List<PostDto> listPosts(int page) {
		int offset = (page - 1) * PAGE_SIZE;
		List<Post> posts = postMapper.findPaginated(offset, PAGE_SIZE);
		return posts.stream().map(p -> {
			User user = userMapper.findById(p.getUserId());
			PostDto dto = new PostDto();
			dto.setId(p.getId());
			dto.setTitle(p.getTitle());
			dto.setCreatorUsername(user != null ? user.getUsername() : null);
			dto.setCreatedAt(p.getCreatedAt());
			return dto;
		}).collect(Collectors.toList());
	}

	public PostDto getPostDetail(Long postId) {
		Post post = postMapper.findById(postId);
		if (post == null)
			throw new IllegalArgumentException("Post not found");
		User user = userMapper.findById(post.getUserId());

		PostDto dto = new PostDto();
		dto.setId(post.getId());
		dto.setTitle(post.getTitle());
		dto.setContent(post.getContent());
		dto.setCreatorUsername(user != null ? user.getUsername() : null);
		dto.setCreatedAt(post.getCreatedAt());
		return dto;
	}

	public int countPosts() {
		return postMapper.countAll();
	}
}
