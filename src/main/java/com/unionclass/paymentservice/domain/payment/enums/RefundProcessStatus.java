package com.unionclass.paymentservice.domain.payment.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.unionclass.paymentservice.common.exception.BaseException;
import com.unionclass.paymentservice.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RefundProcessStatus {

    PENDING("환불대기중"),
    COMPLETED("환불완료"),
    FAILED("환불실패"),
    ;

    private final String refundProcessStatus;

    @JsonValue
    public String getRefundStatus() { return refundProcessStatus; }

    @JsonCreator
    public static RefundProcessStatus fromString(String value) {
        for (RefundProcessStatus refundProcessStatus : RefundProcessStatus.values()) {
            if (refundProcessStatus.refundProcessStatus.equals(value)) {
                return refundProcessStatus;
            }
        }
        throw new BaseException(ErrorCode.INVALID_REFUND_PROCESS_STATUS_VALUE);
    }
}
