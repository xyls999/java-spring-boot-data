package com.example.headline.service;

import com.example.headline.dto.NewsRequest;
import com.example.headline.entity.News;
import com.example.headline.vo.NewsRankVO;
import com.example.headline.vo.PageResult;

import java.util.List;

public interface NewsService {
    PageResult<News> pageNews(int page, int size, String keyword);
    News getById(Long id, Long userId);
    News create(NewsRequest request, Long userId);
    News update(Long id, NewsRequest request, Long userId);
    void delete(Long id, Long userId);
    List<NewsRankVO> rank(String period, int limit);
}
