package com.weihan.ecommerce_backend.utils;

import java.util.Map;

public class ThreadLocalUtil {

    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(Map<String, Object> map) {
        THREAD_LOCAL.set(map);
    }

    public static Map<String, Object> get() {
        return THREAD_LOCAL.get();
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
