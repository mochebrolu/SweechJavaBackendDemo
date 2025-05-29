package com.sweech.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sweech.app.dto.CommentDto;
import com.sweech.app.model.Comment;
import com.sweech.app.service.CommentService;
import com.sweech.app.util.AuthUtil;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // Create a comment (requires authentication)
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createComment(@RequestBody CommentDto request, Authentication authentication) {
        long id = AuthUtil.getUserId(authentication);
        try {
            Comment created = commentService.createComment(id,request.getPostId(), request.getContent());
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "creation_failed", "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "server_error", "message", "Unexpected error"));
        }
    }

    // List comments by postId with cursor pagination (no authentication required)
    @GetMapping(value = "/post/{postId}", produces = "application/json")
    public ResponseEntity<?> listComments(
        @PathVariable Long postId,
        @RequestParam(required = false) Long cursor
    ) {
        try {
            List<CommentDto> commentsPage = commentService.listComments(postId, cursor);
            return ResponseEntity.ok(commentsPage);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "invalid_request", "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "server_error", "message", "Unexpected error"));
        }
    }

    // Delete comment (requires authentication)
    @DeleteMapping(value = "/{commentId}", produces = "application/json")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId, Authentication authentication) {
    	long id = AuthUtil.getUserId(authentication);
        try {
            commentService.deleteComment(commentId, id);
            return ResponseEntity.ok(Map.of("status", "deleted"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(403).body(Map.of("error", "forbidden", "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "server_error", "message", "Unexpected error"));
        }
    }
}
