package com.sweech.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sweech.app.dto.CommentDto;
import com.sweech.app.security.TokenService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/{postId}")
    public ResponseEntity<?> addComment(@PathVariable Long postId, @RequestHeader("Authorization") String token, @RequestBody CommentDto dto) {
        String email = tokenService.getEmail(token.substring(7));
        return ResponseEntity.ok(commentService.add(postId, email, dto));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> list(@PathVariable Long postId, @RequestParam(required = false) Long cursor) {
        return ResponseEntity.ok(commentService.list(postId, cursor));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> delete(@PathVariable Long commentId, @RequestHeader("Authorization") String token) {
        String email = tokenService.getEmail(token.substring(7));
        return ResponseEntity.ok(commentService.delete(commentId, email));
    }
}
