package com.sweech.app.model;

public class Post {
    private Long id;
    private Long userId;         // creator user id (foreign key)
    private String title;        // 1-30 chars
    private String content;      // 1-1000 chars
    private String createdAt;    // ISO8601 string

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
