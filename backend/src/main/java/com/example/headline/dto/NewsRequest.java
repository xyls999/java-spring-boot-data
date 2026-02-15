package com.example.headline.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NewsRequest {
    @NotBlank(message = "标题不能为空")
    @Size(max = 128, message = "标题不能超过128字符")
    private String title;
    @NotBlank(message = "内容不能为空")
    @Size(max = 5000, message = "内容不能超过5000字符")
    private String content;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
