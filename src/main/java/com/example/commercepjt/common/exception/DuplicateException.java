package com.example.commercepjt.common.exception;

//이메일 중복 등 데이터 충돌 시 사용 (409)
public class DuplicateException extends RuntimeException {

    public DuplicateException(String message) {
        super(message);
    }
}
