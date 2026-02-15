package com.example.headline.controller;

import com.example.headline.dto.NewsRequest;
import com.example.headline.entity.News;
import com.example.headline.service.NewsService;
import com.example.headline.util.UserContext;
import com.example.headline.vo.ApiResponse;
import com.example.headline.vo.NewsRankVO;
import com.example.headline.vo.PageResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/list")
    public ApiResponse<PageResult<News>> list(@RequestParam(defaultValue = "1") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam(required = false) String keyword) {
        return ApiResponse.success(newsService.pageNews(page, size, keyword));
    }

    @GetMapping("/{id}")
    public ApiResponse<News> detail(@PathVariable Long id,
                                    @RequestHeader(value = "X-User-Id", required = false) Long userId) {
        return ApiResponse.success(newsService.getById(id, userId));
    }

    @GetMapping("/rank")
    public ApiResponse<List<NewsRankVO>> rank(@RequestParam(defaultValue = "total") String period,
                                              @RequestParam(defaultValue = "10") int limit) {
        return ApiResponse.success(newsService.rank(period, limit));
    }

    @PostMapping("/manage")
    public ApiResponse<News> create(@Valid @RequestBody NewsRequest request) {
        return ApiResponse.success(newsService.create(request, UserContext.getUserId()));
    }

    @PutMapping("/manage/{id}")
    public ApiResponse<News> update(@PathVariable Long id, @Valid @RequestBody NewsRequest request) {
        return ApiResponse.success(newsService.update(id, request, UserContext.getUserId()));
    }

    @DeleteMapping("/manage/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        newsService.delete(id, UserContext.getUserId());
        return ApiResponse.success(null);
    }
}
