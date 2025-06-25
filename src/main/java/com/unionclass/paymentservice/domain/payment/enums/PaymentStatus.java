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

    READY("READY"),
    IN_PROGRESS("IN_PROGRESS"),
    WAITING_FOR_DEPOSIT("WAITING_FOR_DEPOSIT"),
    DONE("DONE"),
    CANCELED("CANCELED"),
    ABORTED("ABORTED"),
    EXPIRED("EXPIRED")
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
