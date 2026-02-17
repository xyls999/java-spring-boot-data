package com.example.headline.controller;

import com.example.headline.service.SocialService;
import com.example.headline.util.UserContext;
import com.example.headline.vo.ApiResponse;
import com.example.headline.vo.UserSimpleVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/social")
public class SocialController {
    private final SocialService socialService;

    public SocialController(SocialService socialService) {
        this.socialService = socialService;
    }

    @PostMapping("/follow/{targetId}")
    public ApiResponse<Void> follow(@PathVariable Long targetId) {
        socialService.follow(UserContext.getUserId(), targetId);
        return ApiResponse.success(null);
    }

    @DeleteMapping("/follow/{targetId}")
    public ApiResponse<Void> unfollow(@PathVariable Long targetId) {
        socialService.unfollow(UserContext.getUserId(), targetId);
        return ApiResponse.success(null);
    }

    @GetMapping("/following")
    public ApiResponse<List<UserSimpleVO>> following() {
        return ApiResponse.success(socialService.following(UserContext.getUserId()));
    }

    @GetMapping("/friends")
    public ApiResponse<List<UserSimpleVO>> friends() {
        return ApiResponse.success(socialService.friends(UserContext.getUserId()));
    }
}
