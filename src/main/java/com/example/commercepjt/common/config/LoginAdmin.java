package com.example.commercepjt.common.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)      // 이 어노테이션은 메서드의 '파라미터'에만 붙일 수 있다고 지정합니다.
@Retention(RetentionPolicy.RUNTIME) // 애플리케이션이 실행되는 동안 이 어노테이션 정보가 유지되도록 합니다.
public @interface LoginAdmin {}