package com.sweech.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sweech.app.model.Comment;

public interface CommentMapper {
	void insert(Comment comment);

	Comment findById(@Param("id") Long id);

	void delete(@Param("id") Long id);

	List<Comment> findCommentsByPostIdWithCursor(@Param("postId") Long postId, @Param("cursor") Long cursor,
			@Param("limit") int limit);
}
