package com.example.commercepjt.common.config;

import com.example.commercepjt.common.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component // 스프링이 이 클래스를 감지하고 (Bean)으로 등록
public class LoginCheckInterceptor implements HandlerInterceptor { // 이 인터페이스를 구현해야 스프링이 "아, 얘 문지기구나" 하고 알아챕니다.

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1. 요청으로 부터 세션을 가져온다. (기존 세션이 없으면 null반환)
        HttpSession session = request.getSession(false);

        // 2. 세션이 아예 없거나, 세션은 있는데 adminId가 저장되어 있지 않다면 비로그인 상태~
        if (session == null || session.getAttribute("adminId") == null) {
            // 401 예외담장자 소환이요
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
        //3. 무사히 세션이 확인되면 true 반환하여 컨트롤러로 보내줌
        return true;
    }
}
/*
preHandle: '컨트롤러가 실행되기 직전(pre)'에 가로채서 실행할 메서드입니다.

HttpServletRequest request: 클라이언트가 보낸 HTTP 요청 정보가 몽땅 들어있습니다. (쿠키, 세션, 헤더, URI 등)

HttpServletResponse response: 클라이언트에게 보낼 응답 정보가 들어있습니다.

Object handler: 이 요청이 도착할 진짜 목적지인 컨트롤러 메서드 객체입니다.

boolean 반환값: 여기서 true를 리턴하면 컨트롤러로 통과, false를 리턴하면 요청이 거기서 그대로 종료됩니다.

@Override 를 쓰는 이유

스프링은 요청이 들어올 때 HandlerInterceptor라는 인터페이스를 상속받은
클래스들을 찾아다니며 "내부적으로 정해진 이름의 메서드"를 자동으로 실행하도록 설계되어 있습니다.
 */