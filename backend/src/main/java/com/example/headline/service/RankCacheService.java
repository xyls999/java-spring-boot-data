package com.example.headline.service;

import com.example.headline.vo.NewsRankVO;

import java.util.List;

public interface RankCacheService {
    List<NewsRankVO> getRank(String period, int limit);
    void refreshBySchedule();
}
