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

    READY("결제준비중"),
    IN_PROGRESS("결제중"),
    WAITING_FOR_DEPOSIT("입금대기중"),
    DONE("결제완료"),
    CANCELED("결제취소"),
    ABORTED("결제실패"),
    EXPIRED("결제만료")
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
