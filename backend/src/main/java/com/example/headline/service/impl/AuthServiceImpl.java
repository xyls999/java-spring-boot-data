package com.example.headline.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.headline.dto.LoginRequest;
import com.example.headline.dto.RegisterRequest;
import com.example.headline.entity.User;
import com.example.headline.exception.BizException;
import com.example.headline.mapper.UserMapper;
import com.example.headline.service.AuthService;
import com.example.headline.util.JwtUtil;
import com.example.headline.util.Md5Util;
import com.example.headline.vo.AuthResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserMapper userMapper, JwtUtil jwtUtil) {
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void register(RegisterRequest request) {
        User existed = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, request.getUsername()));
        if (existed != null) {
            throw new BizException("用户名已存在");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(Md5Util.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        userMapper.insert(user);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, request.getUsername()));
        if (user == null || !user.getPassword().equals(Md5Util.encode(request.getPassword()))) {
            throw new BizException("用户名或密码错误");
        }
        return new AuthResponse(jwtUtil.generateToken(user.getId(), user.getUsername()), user.getUsername());
    }
}
