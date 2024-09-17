package com.weihan.ecommerce_backend.interceptors;

import com.weihan.ecommerce_backend.utils.JwtUtil;
import com.weihan.ecommerce_backend.utils.ThreadLocalUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate stringRedisTemplate;

    public JwtTokenInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // true 放行  false 不放行
        Map<String, Object> claims = new HashMap<>();
        try {
            // 獲取並解析 jwt_token
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if ("jwt_token".equals(cookie.getName())) {
                    claims = JwtUtil.parseToken(cookie.getValue());
                }
            }

        } catch (Exception e) {
            // token 解析異常，回應 401
            response.setStatus(401);

            return false;
        }

        // 從 redis 獲取 cookie 對應的 token
        String id = claims.get("id").toString();
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String redisToken = operations.get(id);
        // 獲取不到 redisToken 驗證失敗，回應 401
        if (redisToken == null) {
            response.setStatus(401);

            return false;
        }

        // 將驗證成功的用戶資訊存入 ThreadLocal
        ThreadLocalUtil.set(claims);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 清空數據，防止內存洩漏
        ThreadLocalUtil.remove();
    }
}
