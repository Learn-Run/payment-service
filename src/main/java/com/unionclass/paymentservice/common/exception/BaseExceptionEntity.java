package com.unionclass.paymentservice.common.exception;

import org.springframework.http.HttpStatus;

public record BaseExceptionEntity(HttpStatus httpStatus, boolean isSuccess, int code, String message) {

    public static BaseExceptionEntity of(HttpStatus httpStatus, boolean isSuccess, int code, String message) {
        return new BaseExceptionEntity(httpStatus, isSuccess, code, message);
    }
}