package com.example.headline.vo;

public class UserSimpleVO {
    private Long id;
    private String username;

    public UserSimpleVO(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
}
