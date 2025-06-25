package com.unionclass.paymentservice.domain.payment.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.unionclass.paymentservice.common.exception.BaseException;
import com.unionclass.paymentservice.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentMethod {

    CARD("CARD"),
    BANK_TRANSFER("BANK_TRANSFER"),
    MOBILE("MOBILE"),
    VIRTUAL_ACCOUNT("VIRTUAL_ACCOUNT")
    ;

    private final String paymentType;

    @JsonValue
    public String getStatus() { return paymentType; }

    @JsonCreator
    public static PaymentMethod fromString(String value) {
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            if (paymentMethod.paymentType.equals(value)) {
                return paymentMethod;
            }
        }
        throw new BaseException(ErrorCode.INVALID_PAYMENT_TYPE_VALUE);
    }
}
