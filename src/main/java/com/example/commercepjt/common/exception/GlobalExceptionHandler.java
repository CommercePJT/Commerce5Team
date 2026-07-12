package com.example.commercepjt.common.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 전역 예외 처리 (공통 담당자 or 팀 협의로 구현)
 *
 * TODO: @ExceptionHandler 메서드 구현
 *  - IllegalArgumentException          -> 400 (비즈니스 규칙 위반)
 *  - MethodArgumentNotValidException   -> 400 (@Valid 검증 실패)
 *  - UnauthorizedException             -> 401 (인증 실패)
 *  - ForbiddenException                -> 403 (접근 불가 계정)
 *  - NotFoundException                 -> 404 (리소스 없음)
 *  - DuplicateException                -> 409 (이메일 중복 등)
 *  응답 바디는 ErrorResponse(status, message) 사용
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

}
