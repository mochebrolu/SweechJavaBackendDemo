package com.sweech.app.model;

public class User {
    private Long id;
    private String email;        // user ID (email format)
    private String password;     // bcrypt hashed password
    private String username;     // Korean name 1-10 chars
    private String registeredAt; // ISO8601 string

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRegisteredAt() { return registeredAt; }
    public void setRegisteredAt(String registeredAt) { this.registeredAt = registeredAt; }
}
