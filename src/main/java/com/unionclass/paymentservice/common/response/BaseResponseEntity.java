package com.unionclass.paymentservice.common.response;

import org.springframework.http.HttpStatus;

public record BaseResponseEntity<T>(HttpStatus httpStatus, boolean isSuccess, int code, String message, T result) {

    public BaseResponseEntity(String message, T result) {this(HttpStatus.OK, true, 200, message, result);}

    public BaseResponseEntity(String message) {
        this(HttpStatus.OK, true, 200, message, null);
    }
}
