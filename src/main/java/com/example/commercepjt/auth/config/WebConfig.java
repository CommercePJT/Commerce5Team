package com.example.commercepjt.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor

public class WebConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginInterceptor)
                // 인터셉터를 적용할 URL _ 모든 URL에 적용
                .addPathPatterns("/**")
                // 로그인 없이 접근할 수 있는 URL _ 회원가입, 로그인은 제외
                .excludePathPatterns(
                        "/admins/signup",
                        "/admins/login",
                        "/error");
    }
}
