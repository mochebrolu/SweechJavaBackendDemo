package com.sweech.app.model;


public class Comment {
    private Long id;
    private Long postId;         // foreign key to post
    private Long userId;         // creator user id
    private String content;      // 1-500 chars
    private String createdAt;    // ISO8601 string

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
