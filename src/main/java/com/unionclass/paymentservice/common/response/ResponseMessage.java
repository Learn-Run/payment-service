package com.unionclass.paymentservice.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {

    SUCCESS_REQUEST_PAYMENT("결제 요청에 성공하였습니다."),
    SUCCESS_CONFIRM_PAYMENT("결제 승인에 성공하였습니다."),
    SUCCESS_CANCEL_PAYMENT("결제 취소 및 환불에 성공하였습니다."),
    SUCCESS_CREATE_ORDER("주문 생성에 성공하였습니다."),
    SUCCESS_GET_PAYMENT_DETAILS_BY_PAYMENT_KEY("paymentKey 로 결제 상세 정보 단건 조회하는데 성공하였습니다."),
    SUCCESS_GET_PAYMENT_DETAILS_BY_ORDER_ID("orderId 로 결제 상세 정보 단건 조회하는데 성공하였습니다."),
    SUCCESS_GET_PAYMENT_SUMMARY("결제 요약 정보 단건 조회에 성공하였습니다."),
    SUCCESS_CREATE_ORDER_AND_REQUEST_PAYMENT("주문 생성 및 결제 요청에 성공하였습니다."),
    SUCCESS("요청이 성공적으로 처리되었습니다."),
    ;

    private final String message;
}
