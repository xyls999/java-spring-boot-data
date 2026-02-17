package com.example.headline.service;

import com.example.headline.vo.UserSimpleVO;

import java.util.List;

public interface SocialService {
    void follow(Long userId, Long targetId);
    void unfollow(Long userId, Long targetId);
    List<UserSimpleVO> following(Long userId);
    List<UserSimpleVO> friends(Long userId);
}
