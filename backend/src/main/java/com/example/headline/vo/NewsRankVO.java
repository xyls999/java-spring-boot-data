package com.example.headline.vo;

public class NewsRankVO {
    private Long newsId;
    private String title;
    private String authorName;
    private Long score;

    public Long getNewsId() { return newsId; }
    public void setNewsId(Long newsId) { this.newsId = newsId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
    public Long getScore() { return score; }
    public void setScore(Long score) { this.score = score; }
}
