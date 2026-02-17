package com.example.headline.service;

import com.example.headline.dto.LoginRequest;
import com.example.headline.dto.RegisterRequest;
import com.example.headline.vo.AuthResponse;

public interface AuthService {
    void register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
