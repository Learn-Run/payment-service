package com.unionclass.paymentservice.domain.payment.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.unionclass.paymentservice.common.exception.BaseException;
import com.unionclass.paymentservice.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {

    PAYMENT_STATUS_PENDING("결제대기"),
    PAYMENT_STATUS_PROCESSING("결제중"),
    PAYMENT_STATUS_COMPLETED("결제완료"),
    PAYMENT_STATUS_CANCELED("결제취소")
    ;

    private final String paymentStatus;

    @JsonValue
    public String getStatus() { return paymentStatus; }

    @JsonCreator
    public static PaymentStatus fromString(String value) {
        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            if (paymentStatus.paymentStatus.equals(value)) {
                return paymentStatus;
            }
        }
        throw new BaseException(ErrorCode.INVALID_PAYMENT_STATUS_VALUE);
    }
}
