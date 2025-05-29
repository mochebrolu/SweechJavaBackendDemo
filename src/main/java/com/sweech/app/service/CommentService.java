package com.sweech.app.service;


import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sweech.app.dto.CommentDto;
import com.sweech.app.mapper.CommentMapper;
import com.sweech.app.mapper.PostMapper;
import com.sweech.app.mapper.UserMapper;
import com.sweech.app.model.Comment;
import com.sweech.app.model.Post;
import com.sweech.app.model.User;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PostMapper postMapper;

    private static final int PAGE_SIZE = 10;

    public Comment createComment(Long userId, Long postId, String content) {
        // Ensure post exists
        Post post = postMapper.findById(postId);
        if (post == null) throw new IllegalArgumentException("Post not found");

        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setPostId(postId);
        comment.setContent(content);
        comment.setCreatedAt(Instant.now().toString());

        commentMapper.insert(comment);
        return comment;
    }

    public List<CommentDto> listComments(Long postId, Long cursor) {
        // cursor is last comment ID fetched; null means start from latest
        List<Comment> comments = commentMapper.findCommentsByPostIdWithCursor(postId, cursor, PAGE_SIZE);
        return comments.stream().map(c -> {
            User user = userMapper.findById(c.getUserId());
            CommentDto dto = new CommentDto();
            dto.setId(c.getId());
            dto.setContent(c.getContent());
            dto.setUsername(user != null ? user.getUsername() : null);
            dto.setCreatedAt(c.getCreatedAt());
            return dto;
        }).collect(Collectors.toList());
    }

    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentMapper.findById(commentId);
        if (comment == null) throw new IllegalArgumentException("Comment not found");

        Post post = postMapper.findById(comment.getPostId());
        if (post == null) throw new IllegalArgumentException("Post not found");

        // Only comment creator or post creator can delete comment
        if (!comment.getUserId().equals(userId) && !post.getUserId().equals(userId)) {
            throw new SecurityException("Not authorized to delete this comment");
        }

        commentMapper.delete(commentId);
    }
}
