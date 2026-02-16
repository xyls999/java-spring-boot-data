package com.example.headline.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.headline.entity.News;
import com.example.headline.vo.NewsRankVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

public interface NewsMapper extends BaseMapper<News> {
    @Select("""
            SELECT n.id AS newsId, n.title AS title, u.username AS authorName, n.heat_score AS score
            FROM news n
            LEFT JOIN users u ON u.id = n.author_id
            ORDER BY n.heat_score DESC, n.updated_at DESC
            LIMIT #{limit}
            """)
    List<NewsRankVO> totalRank(@Param("limit") int limit);

    @Select("""
            SELECT n.id AS newsId, n.title AS title, u.username AS authorName, COUNT(v.id) AS score
            FROM news n
            LEFT JOIN users u ON u.id = n.author_id
            JOIN news_view v ON v.news_id = n.id
            WHERE v.viewed_at >= #{startTime}
            GROUP BY n.id, n.title, u.username
            ORDER BY score DESC, n.updated_at DESC
            LIMIT #{limit}
            """)
    List<NewsRankVO> timedRank(@Param("startTime") LocalDateTime startTime, @Param("limit") int limit);

    @Update("UPDATE news SET heat_score = heat_score + 1 WHERE id = #{newsId}")
    int increaseHeat(@Param("newsId") Long newsId);
}
