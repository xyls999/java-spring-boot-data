package com.example.headline.util;

public class UserContext {
    private static final ThreadLocal<Long> USER_HOLDER = new ThreadLocal<>();

    public static void setUserId(Long userId) {
        USER_HOLDER.set(userId);
    }

    public static Long getUserId() {
        return USER_HOLDER.get();
    }

    public static void clear() {
        USER_HOLDER.remove();
    }
}
