package com.example.commercepjt.common.exception;

/** 접근 불가 시 사용 (403) - 승인대기/거부/정지/비활성 계정 로그인 등 */
public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message) {
        super(message);
    }
}
