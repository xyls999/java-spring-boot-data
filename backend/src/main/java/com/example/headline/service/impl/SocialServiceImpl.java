package com.example.headline.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.headline.entity.User;
import com.example.headline.entity.UserFollow;
import com.example.headline.exception.BizException;
import com.example.headline.mapper.UserFollowMapper;
import com.example.headline.mapper.UserMapper;
import com.example.headline.service.SocialService;
import com.example.headline.vo.UserSimpleVO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SocialServiceImpl implements SocialService {
    private final UserFollowMapper userFollowMapper;
    private final UserMapper userMapper;

    public SocialServiceImpl(UserFollowMapper userFollowMapper, UserMapper userMapper) {
        this.userFollowMapper = userFollowMapper;
        this.userMapper = userMapper;
    }

    @Override
    public void follow(Long userId, Long targetId) {
        if (userId.equals(targetId)) {
            throw new BizException("不能关注自己");
        }
        User target = userMapper.selectById(targetId);
        if (target == null) {
            throw new BizException("目标用户不存在");
        }
        UserFollow exists = userFollowMapper.selectOne(new LambdaQueryWrapper<UserFollow>()
                .eq(UserFollow::getUserId, userId)
                .eq(UserFollow::getFollowUserId, targetId));
        if (exists != null) {
            return;
        }
        UserFollow relation = new UserFollow();
        relation.setUserId(userId);
        relation.setFollowUserId(targetId);
        relation.setCreatedAt(LocalDateTime.now());
        userFollowMapper.insert(relation);
    }

    @Override
    public void unfollow(Long userId, Long targetId) {
        userFollowMapper.delete(new LambdaQueryWrapper<UserFollow>()
                .eq(UserFollow::getUserId, userId)
                .eq(UserFollow::getFollowUserId, targetId));
    }

    @Override
    public List<UserSimpleVO> following(Long userId) {
        List<UserFollow> followings = userFollowMapper.selectList(new LambdaQueryWrapper<UserFollow>()
                .eq(UserFollow::getUserId, userId));
        return toVo(followings.stream().map(UserFollow::getFollowUserId).collect(Collectors.toSet()));
    }

    @Override
    public List<UserSimpleVO> friends(Long userId) {
        Set<Long> iFollow = userFollowMapper.selectList(new LambdaQueryWrapper<UserFollow>()
                        .eq(UserFollow::getUserId, userId))
                .stream().map(UserFollow::getFollowUserId).collect(Collectors.toSet());
        Set<Long> followMe = userFollowMapper.selectList(new LambdaQueryWrapper<UserFollow>()
                        .eq(UserFollow::getFollowUserId, userId))
                .stream().map(UserFollow::getUserId).collect(Collectors.toSet());
        iFollow.retainAll(followMe);
        return toVo(iFollow);
    }

    private List<UserSimpleVO> toVo(Set<Long> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }
        return userMapper.selectBatchIds(ids).stream()
                .map(u -> new UserSimpleVO(u.getId(), u.getUsername()))
                .collect(Collectors.toList());
    }
}
