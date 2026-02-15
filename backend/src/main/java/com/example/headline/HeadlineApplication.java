package com.example.headline;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.headline.mapper")
public class HeadlineApplication {
    public static void main(String[] args) {
        SpringApplication.run(HeadlineApplication.class, args);
    }
}
