package com.sweech.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sweech.app.dto.PostDetailDto;
import com.sweech.app.dto.PostDto;
import com.sweech.app.model.Post;

public interface PostMapper {
	void insert(Post post);

	Post findById(@Param("id") Long id);

	List<Post> findPaginated(@Param("offset") int offset, @Param("limit") int limit);

	int countAll();

	PostDetailDto findDetailById(@Param("id") Long id);
}
