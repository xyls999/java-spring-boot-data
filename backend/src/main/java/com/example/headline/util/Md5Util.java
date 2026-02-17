package com.example.headline.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Md5Util {
    public static String encode(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(text.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new IllegalStateException("MD5计算失败", e);
        }
    }
}
