package com.example.commercepjt.common.exception;

/** 인증 실패 시 사용 (401) - 로그인 실패, 미로그인 요청 등 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }
}
