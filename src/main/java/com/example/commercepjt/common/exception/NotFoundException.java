package com.example.commercepjt.common.exception;

//존재하지 않는 리소스 조회 시 사용 (404)
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
