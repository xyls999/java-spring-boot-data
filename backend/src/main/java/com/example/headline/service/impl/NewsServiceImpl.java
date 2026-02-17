package com.example.headline.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.headline.dto.NewsRequest;
import com.example.headline.entity.News;
import com.example.headline.exception.BizException;
import com.example.headline.mapper.NewsMapper;
import com.example.headline.service.HeatAsyncService;
import com.example.headline.service.NewsService;
import com.example.headline.service.RankCacheService;
import com.example.headline.vo.NewsRankVO;
import com.example.headline.vo.PageResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    private final NewsMapper newsMapper;
    private final HeatAsyncService heatAsyncService;
    private final RankCacheService rankCacheService;

    public NewsServiceImpl(NewsMapper newsMapper,
                           HeatAsyncService heatAsyncService,
                           RankCacheService rankCacheService) {
        this.newsMapper = newsMapper;
        this.heatAsyncService = heatAsyncService;
        this.rankCacheService = rankCacheService;
    }

    @Override
    public PageResult<News> pageNews(int page, int size, String keyword) {
        LambdaQueryWrapper<News> wrapper = new LambdaQueryWrapper<News>()
                .like(keyword != null && !keyword.isBlank(), News::getTitle, keyword)
                .orderByDesc(News::getUpdatedAt);
        Page<News> pager = newsMapper.selectPage(new Page<>(page, size), wrapper);
        return new PageResult<>(pager.getTotal(), pager.getCurrent(), pager.getSize(), pager.getRecords());
    }

    @Override
    public News getById(Long id, Long userId) {
        News news = baseGetById(id);
        heatAsyncService.increaseHeatAndLog(id, userId);
        return news;
    }

    @Override
    public News create(NewsRequest request, Long userId) {
        News news = new News();
        news.setTitle(request.getTitle());
        news.setContent(request.getContent());
        news.setAuthorId(userId);
        news.setHeatScore(0L);
        news.setCreatedAt(LocalDateTime.now());
        news.setUpdatedAt(LocalDateTime.now());
        newsMapper.insert(news);
        return news;
    }

    @Override
    public News update(Long id, NewsRequest request, Long userId) {
        News db = baseGetById(id);
        if (!db.getAuthorId().equals(userId)) {
            throw new BizException("仅作者可修改新闻");
        }
        db.setTitle(request.getTitle());
        db.setContent(request.getContent());
        db.setUpdatedAt(LocalDateTime.now());
        newsMapper.updateById(db);
        return db;
    }

    @Override
    public void delete(Long id, Long userId) {
        News db = baseGetById(id);
        if (!db.getAuthorId().equals(userId)) {
            throw new BizException("仅作者可删除新闻");
        }
        newsMapper.deleteById(id);
    }

    @Override
    public List<NewsRankVO> rank(String period, int limit) {
        return rankCacheService.getRank(period, limit);
    }

    private News baseGetById(Long id) {
        News news = newsMapper.selectById(id);
        if (news == null) {
            throw new BizException("新闻不存在");
        }
        return news;
    }
}
