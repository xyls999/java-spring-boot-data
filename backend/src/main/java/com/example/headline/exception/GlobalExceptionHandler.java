package com.example.headline.exception;

import com.example.headline.vo.ApiResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BizException.class)
    public ApiResponse<Void> handleBiz(BizException e) {
        return ApiResponse.fail(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Void> handleValid(MethodArgumentNotValidException e) {
        return ApiResponse.fail(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(JwtException.class)
    public ApiResponse<Void> handleJwt(JwtException e) {
        return ApiResponse.fail("登录已过期，请重新登录");
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleException(Exception e) {
        return ApiResponse.fail("系统异常：" + e.getMessage());
    }
}
