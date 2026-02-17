package com.example.headline.vo;

public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.code = 0;
        response.message = "success";
        response.data = data;
        return response;
    }

    public static <T> ApiResponse<T> fail(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.code = 1;
        response.message = message;
        return response;
    }

    public int getCode() { return code; }
    public String getMessage() { return message; }
    public T getData() { return data; }
}
