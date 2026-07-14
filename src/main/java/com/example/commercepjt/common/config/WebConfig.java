package com.example.commercepjt.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor // 의존성 자동주입
public class WebConfig implements WebMvcConfigurer { // 이걸 상속받아야 인터셉터를 등록할 수 있는 자격이 주어집니다. (스프링 MVC의 핵심 설정)

    //리졸버와 인터셉터를 주입받을 필드 선언
    private final LoginAdminArgumentResolver loginAdminArgumentResolver;
    private final LoginCheckInterceptor loginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .order(1) // 여러 인터셉터가 있을때 실행되는 순서
                .addPathPatterns("/**")   // 모든 URL요청에 대해 이 인텁셉터를 실행하되
                .excludePathPatterns(     // 아래 예외 주소들은 검사없이 통과시킨다.
                        "/admins/signup", // 관리자 회원가입 API주소
                        "/admins/login",   //관리자 로그인 API 주소
                        "/h2-console/**",
                        "/error"          // 스프링부트 기본에러 페이지 경로
                );

    }

    // 2. [추가] 아규먼트 리졸버 등록 방을 열고 비서를 리스트에 넣어줍니다.
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginAdminArgumentResolver);
    }

}

/**
@Configuration:
 스프링에게 "이 클래스는 서버가 켜질 때 읽어야 하는 설정 파일이야"라고 알려주는 표지판입니다.
 스프링은 이 어노테이션이 붙은 클래스를 보고 내부의 설정들을 애플리케이션에 적용합니다.

addInterceptors:
 WebMvcConfigurer가 가진 메서드 중 하나로, "인터셉터를 등록하는 방"입니다.
역시 스프링의 규칙을 따르기 위해 @Override를 사용했습니다.

InterceptorRegistry registry:
 스프링이 제공하는 '인터셉터 등록 장부'입니다. 이 장부에 우리가 만든 문지기를 기입해야 비로소 작동합니다.

.addPathPatterns("/**")

* 별한개 : ex) ("/요청API/*") 라고 적으면
/products/1 (O 감시함)
/products/lists (O 감시함)
/products/detail/1 (X 감시 못 함! detail 다음에 /1이 한 단계 더 들어갔기 때문입니다.)

* 별 두개 : 별이 두 개일 때는 "현재 위치 밑으로 생기는 모든 하위 주소를 깊이에 상관없이 전부 다" 감시하겠다는 뜻입니다.
*/


/**
 * [인터셉터(Interceptor) 도입 배경 및 장단점]
 * <p>
 * 1. 장점 (도입 이유)
 * - 고성능/저메모리 최적화: 세션 정보는 서버 메모리(RAM)에서 0에 가까운 속도로 조회되므로 CPU/DB 부하가 없음.
 * - 관심사의 분리: 컨트롤러가 인증 로직을 신경 쓰지 않고 비즈니스 로직에만 집중 가능
 * - 중복 제거 & 유지보수성: 수많은 컨트롤러의 로그인 체크 코드를 단 한 곳에서 공통 관리
 * - 보안성 향상: 주소 패턴(/**) 기반으로 작동하여 개발자의 실수(체크 코드 누락)로 인한 보안 구멍 원천 차단
 * - 효율적인 입구 컷: 컨트롤러 실행 전(객체 변환/검증 단계 전) 최앞단에서 빠르게 비로그인 유저를 차단
 * <p>
 * 2. 단점 (주의할 점)
 * - 예외 주소 관리의 번거로움: 로그인 없이 허용할 주소(ex: 회원가입, 로그인 등)를 WebConfig에 정확히 누락 없이 등록해야 함
 * - 세밀한 로직 제어의 한계: URL 주소 패턴 기반으로만 동작하므로, HTTP 메서드(GET/POST)나 요청 본문(Body) 데이터의 내용에 따른 세부적인 분기 처리가 비교적 까다로움
 */