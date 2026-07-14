/*
package com.example.commercepjt.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                // API 테스트를 위해 CSRF 검사 끄기
                .csrf(csrf -> csrf.disable())

                // 어떤 요청을 허용할지 설정
                .authorizeHttpRequests(auth -> auth

                        // 회원가입과 로그인은 누구나 접근 가능
                        .requestMatchers(
                                "/admins/signup",
                                "/admins/login",
                                "/admins/logout"
                        ).permitAll()

                        // 나머지는 로그인한 사용자만 접근 가능
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
*/
