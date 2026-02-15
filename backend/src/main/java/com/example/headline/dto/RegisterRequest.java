package com.example.headline.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 30, message = "用户名长度需在3到30之间")
    private String username;
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 64, message = "密码长度需在6到64之间")
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
