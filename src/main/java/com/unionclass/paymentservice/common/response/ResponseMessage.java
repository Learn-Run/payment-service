package com.unionclass.paymentservice.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {

//    SUCCESS_SIGN_UP("회원가입에 성공하였습니다."),
    ;

    private final String message;
}
