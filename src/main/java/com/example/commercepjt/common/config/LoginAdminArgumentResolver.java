package com.example.commercepjt.common.config;

import com.example.commercepjt.common.annotation.LoginAdmin;
import com.example.commercepjt.common.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer; // 👈 1. 임포트 경로 확실하게 수정!

@Component
public class LoginAdminArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 1단계: 이 비서(리졸버)가 어떤 파라미터를 담당할지 필터링하는 검문소입니다.
     * 컨트롤러 메서드의 파라미터에 @LoginAdmin 어노테이션이 붙어있고, 타입이 Long(Id)인 경우에만 작동합니다.
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginAdmin.class)
                && parameter.getParameterType().equals(Long.class);
    }

    /**
     * 2단계: supportsParameter가 true를 반환하면 실제로 실행되는 몸통입니다.
     * 세션을 뒤져서 로그인한 관리자의 ID를 꺼낸 뒤, 컨트롤러 파라미터에 자동으로 꽂아줍니다.
     */
    @Override
    public Object resolveArgument(@NotNull MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        // 스프링의 웹 요청 객체에서 순수한 서블릿(HttpServletRequest) 객체를 꺼냅니다.
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        // 만약의 상황을 대비한 null 방어 (request가 없는 특수 상황 대비)
        if (request == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        // 기존 세션이 있으면 가져오고, 없으면 새로 만들지 않고 null을 반환합니다.
        HttpSession session = request.getSession(false);

        // 세션이 없거나 세션 내부에 관리자 식별자(adminId) 이름표가 없다면 예외를 터뜨립니다.
        if (session == null || session.getAttribute("adminId") == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        // 2. 캐스팅 경고 해결: 세션에서 나온 Object를 안전하게 Long으로 변환하여 반환합니다.
        return session.getAttribute("adminId");
    }
}

/**
 * [스프링 아규먼트 리졸버(Argument Resolver) 도입 배경 및 장단점]

 * 1. 도입 이유 (가장 큰 이유)
 *   - 컨트롤러에서 세션을 뒤져 데이터를 꺼내고 형변환하는 지저분한 중복 코드를 싹 제거하기 위함.
 *   - 파라미터에 어노테이션(@LoginAdmin)만 붙이면 세션 데이터가 자동 주입되는 깔끔한 통로 확보.

 * 2. 장점
 *   - 컨트롤러 가독성 향상: 핵심 비즈니스 로직에만 집중할 수 있어 코드가 극단적으로 깔끔해짐.
 *   - 높은 확장성: 공지사항, 쿠폰 등 새로운 백오피스 기능(도메인)이 추가돼도 어노테이션 하나로 즉시 재사용 가능.
 *   - 테스트 편의성: 세션(Mock)을 복잡하게 모킹할 필요 없이, 파라미터에 원하는 ID 값만 툭 던져서 테스트 가능.

 * 3. 단점
 *   - 초기 공수 비용: 도메인이 적은 개발 초기에는 설정 클래스들을 모두 만들어야 해서 과한 엔지니어링처럼 느껴짐.
 *   - 마법 같은 동작(은닉화): 데이터가 주입되는 과정이 눈에 보이지 않아, 스프링 내부 구조를 모르면 디버깅이 조금 까다로움.
 */