package com.example.headline.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

@TableName("news_view")
public class NewsView {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long newsId;
    private Long userId;
    private LocalDateTime viewedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getNewsId() { return newsId; }
    public void setNewsId(Long newsId) { this.newsId = newsId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public LocalDateTime getViewedAt() { return viewedAt; }
    public void setViewedAt(LocalDateTime viewedAt) { this.viewedAt = viewedAt; }
}
