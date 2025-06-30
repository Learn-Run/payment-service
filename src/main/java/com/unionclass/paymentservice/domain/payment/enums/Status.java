package com.unionclass.paymentservice.domain.payment.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.unionclass.paymentservice.common.exception.BaseException;
import com.unionclass.paymentservice.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {

    READY("READY"),
    IN_PROGRESS("IN_PROGRESS"),
    WAITING_FOR_DEPOSIT("WAITING_FOR_DEPOSIT"),
    DONE("DONE"),
    CANCELED("CANCELED"),
    ABORTED("ABORTED"),
    EXPIRED("EXPIRED")
    ;

    private final String status;

    @JsonValue
    public String getStatus() { return status; }

    @JsonCreator
    public static Status fromString(String value) {
        for (Status status : Status.values()) {
            if (status.status.equals(value)) {
                return status;
            }
        }
        throw new BaseException(ErrorCode.INVALID_PAYMENT_STATUS_VALUE);
    }
}
