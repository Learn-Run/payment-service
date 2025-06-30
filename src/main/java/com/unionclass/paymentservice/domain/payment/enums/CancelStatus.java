package com.unionclass.paymentservice.domain.payment.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.unionclass.paymentservice.common.exception.BaseException;
import com.unionclass.paymentservice.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CancelStatus {

    READY("READY"),
    IN_PROGRESS("IN_PROGRESS"),
    DONE("DONE"),
    CANCELED("CANCELED"),
    ABORTED("ABORTED"),
    EXPIRED("EXPIRED")
    ;

    private final String cancelStatus;

    @JsonValue
    public String getCancelStatus() { return cancelStatus; }

    @JsonCreator
    public static CancelStatus fromString(String value) {
        for (CancelStatus cancelStatus : CancelStatus.values()) {
            if (cancelStatus.cancelStatus.equals(value)) {
                return cancelStatus;
            }
        }
        throw new BaseException(ErrorCode.INVALID_PAYMENT_CANCEL_STATUS_VALUE);
    }
}
