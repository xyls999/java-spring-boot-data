package com.example.headline.controller;

import com.example.headline.vo.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/system")
public class SystemController {
    private final String nodeId;
    private final LocalDateTime bootTime = LocalDateTime.now();

    public SystemController(@Value("${app.node-id:single-node}") String nodeId) {
        this.nodeId = nodeId;
    }

    @GetMapping("/node")
    public ApiResponse<Map<String, Object>> node() {
        return ApiResponse.success(Map.of(
                "nodeId", nodeId,
                "bootTime", bootTime,
                "message", "用于分布式部署下的节点识别"
        ));
    }
}
