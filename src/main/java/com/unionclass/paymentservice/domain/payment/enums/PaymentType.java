package com.unionclass.paymentservice.domain.payment.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.unionclass.paymentservice.common.exception.BaseException;
import com.unionclass.paymentservice.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentType {

    PAYMENT_TYPE_CARD("카드"),
    PAYMENT_TYPE_CASH("현금"),
    PAYMENT_TYPE_BANK_TRANSFER("계좌이체"),
    PAYMENT_TYPE_MOBILE("휴대폰결제"),
    PAYMENT_TYPE_VIRTUAL_ACCOUNT("가상계좌")
    ;

    private final String paymentType;

    @JsonValue
    public String getStatus() { return paymentType; }

    @JsonCreator
    public static PaymentType fromString(String value) {
        for (PaymentType paymentType : PaymentType.values()) {
            if (paymentType.paymentType.equals(value)) {
                return paymentType;
            }
        }
        throw new BaseException(ErrorCode.INVALID_PAYMENT_TYPE_VALUE);
    }
}
