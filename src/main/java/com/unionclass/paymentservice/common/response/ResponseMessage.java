package com.unionclass.paymentservice.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {

    SUCCESS_CREATE_PAYMENT("결제 생성에 성공하였습니다."),
    SUCCESS_CONFIRM_PAYMENT("결제 승인에 성공하였습니다.");

    private final String message;
}
