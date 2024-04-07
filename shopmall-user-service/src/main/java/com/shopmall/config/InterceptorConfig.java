package com.shopmall.config;

import com.shopmall.interceptor.LoginInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author xiao
 * @data 2024/4/7 8:47
 */
public class InterceptorConfig implements WebMvcConfigurer {
    public void addInterceptor(InterceptorRegistry registry){
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/api/user/*/**","/api/address/*/**")
                .excludePathPatterns("/api/user/*/send_code", "/api/user/*/captcha", "/api/user/*/register", "/api/user/*/login", "/api/user/*/upload");
    }
}
