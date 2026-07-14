package com.example.commercepjt.auth.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest servletRequest,
                             HttpServletResponse servletResponse,
                             Object hanbler) throws Exception{

        // 기존 세션 조회
        // false를 사용하면 세션이 없을 때 새로 만들지 않고 null 사용
        HttpSession session = servletRequest.getSession(false);

        // 세션이 없거나 로그인 정보가 없으면 요청 차단
        if (session == null || session.getAttribute("adminId") == null) {
            // 로그인 하지 않은 상태의 요청이니깐 401 상태 코드를 반환
            servletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            // 응답 타입 설정
            servletResponse.setContentType("application/json");
            servletResponse.setCharacterEncoding("UTF-8");
            // 응답 메시지 작성
            servletResponse.getWriter().write("""
                    {
                    "message: "로그인이 필요합니다.
                    }
                    """);

            return false;
        }

        //로그인 상태라면 요청 전달(controller 로)
        return true;
    }
}
