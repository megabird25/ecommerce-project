package com.weihan.ecommerce_backend.config;

import com.weihan.ecommerce_backend.interceptors.JwtTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JwtTokenInterceptor jwtTokenInterceptor;

    private static final List<String> BYPASS_PATHS = List.of(
            "/user/update",
            "/user/password",
            "/user/avatar",
            "/user/address/**",
            "/user/order");

    public WebConfig(JwtTokenInterceptor jwtTokenInterceptor) {
        this.jwtTokenInterceptor = jwtTokenInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 只攔截需要的路徑，多個路徑可以使用數列
        registry.addInterceptor(jwtTokenInterceptor).addPathPatterns(BYPASS_PATHS);
    }
}
