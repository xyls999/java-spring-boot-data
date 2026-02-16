package com.example.headline.service.impl;

import com.example.headline.mapper.NewsMapper;
import com.example.headline.mapper.SystemMapper;
import com.example.headline.service.RankCacheService;
import com.example.headline.vo.NewsRankVO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RankCacheServiceImpl implements RankCacheService {
    private static final String LOCK_KEY = "headline:rank:refresh";
    private final Map<String, List<NewsRankVO>> rankCache = new ConcurrentHashMap<>();

    private final NewsMapper newsMapper;
    private final SystemMapper systemMapper;

    public RankCacheServiceImpl(NewsMapper newsMapper, SystemMapper systemMapper) {
        this.newsMapper = newsMapper;
        this.systemMapper = systemMapper;
        warmUp();
    }

    @Override
    public List<NewsRankVO> getRank(String period, int limit) {
        String safePeriod = normalizePeriod(period);
        List<NewsRankVO> cached = rankCache.get(safePeriod);
        if (cached == null || cached.isEmpty()) {
            cached = queryRank(safePeriod, Math.max(limit, 10));
            rankCache.put(safePeriod, cached);
        }
        return cached.stream().limit(limit).toList();
    }

    @Override
    @Scheduled(fixedDelayString = "${app.rank.refresh-ms:60000}")
    public void refreshBySchedule() {
        Integer locked = systemMapper.tryLock(LOCK_KEY, 1);
        if (locked == null || locked != 1) {
            return;
        }
        try {
            warmUp();
        } finally {
            systemMapper.releaseLock(LOCK_KEY);
        }
    }

    private void warmUp() {
        rankCache.put("total", queryRank("total", 20));
        rankCache.put("day", queryRank("day", 20));
        rankCache.put("week", queryRank("week", 20));
    }

    private List<NewsRankVO> queryRank(String period, int limit) {
        if ("day".equals(period)) {
            return newsMapper.timedRank(LocalDateTime.now().minusDays(1), limit);
        }
        if ("week".equals(period)) {
            return newsMapper.timedRank(LocalDateTime.now().minusWeeks(1), limit);
        }
        return newsMapper.totalRank(limit);
    }

    private String normalizePeriod(String period) {
        if ("day".equalsIgnoreCase(period)) return "day";
        if ("week".equalsIgnoreCase(period)) return "week";
        return "total";
    }
}
