package com.example.commercepjt.common.exception;

import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 전역 예외 처리 (공통 담당자 or 팀 협의로 구현)
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

    // 400 - 비즈니스 규칙 위반
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, e.getMessage()));
    }

    // 400 - @Valid 검증 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException e) {

        String message = "입력값이 올바르지 않습니다.";   // 기본 메시지

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            message = fieldError.getDefaultMessage();   // 첫 번째 검증 실패 메시지
            break;
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, message));
    }

    // 400 - PathVariable 또는 Query Parameter 타입 변환 실패
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400,e.getName() + " 값의 형식이 올바르지 않습니다."));
    }

    // 400 - 잘못된 JSON 또는 RequestBody 값 변환 실패
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMessageNotReadable(HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400,"요청 본문의 형식 또는 값이 올바르지 않습니다."));
    }

    // 400 - 존재하지 않는 필드로 정렬 요청 (ex: ?sort = asdf)
    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<ErrorResponse> handlePropertyReference(PropertyReferenceException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, "존재하지 않는 정렬 기준입니다: " + e.getPropertyName()));
    }

    // 401 - 인증 실패
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(401, e.getMessage()));
    }

    // 403 - 접근 불가 계정
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbidden(ForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(403, e.getMessage()));
    }

    // 404 - 리소스 없음
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, e.getMessage()));
    }

    // 409 - 중복 (이메일 등)
    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(DuplicateException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(409, e.getMessage()));
    }


}
