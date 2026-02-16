package com.example.headline.service.impl;

import com.example.headline.entity.News;
import com.example.headline.entity.NewsView;
import com.example.headline.mapper.NewsMapper;
import com.example.headline.mapper.NewsViewMapper;
import com.example.headline.service.HeatAsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HeatAsyncServiceImpl implements HeatAsyncService {
    private final NewsMapper newsMapper;
    private final NewsViewMapper newsViewMapper;

    public HeatAsyncServiceImpl(NewsMapper newsMapper, NewsViewMapper newsViewMapper) {
        this.newsMapper = newsMapper;
        this.newsViewMapper = newsViewMapper;
    }

    @Override
    @Async("heatExecutor")
    public void increaseHeatAndLog(Long newsId, Long userId) {
        News news = newsMapper.selectById(newsId);
        if (news != null) {
            long current = news.getHeatScore() == null ? 0L : news.getHeatScore();
            news.setHeatScore(current + 1);
            newsMapper.updateById(news);
        }

        NewsView log = new NewsView();
        log.setNewsId(newsId);
        log.setUserId(userId);
        log.setViewedAt(LocalDateTime.now());
        newsViewMapper.insert(log);
    }
}
