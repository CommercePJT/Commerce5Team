package com.example.commercepjt.auth.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 이메일 중복
    DUPLICATE_EMAIL(HttpStatus.CONFLICT,"이미 사용 중인 이메일입니다."),

    // 관리자 조회 실패
    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND,"관리자를 찾을 수 없습니다."),

    // 비밀번호 불일치
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED,"비밀번호가 일치하지 않습니다.");

    // HTTP 상태 코드
    private final HttpStatus status;
    // 클라이언트에게 전달할 에러 메시지
    private final String message;

}
