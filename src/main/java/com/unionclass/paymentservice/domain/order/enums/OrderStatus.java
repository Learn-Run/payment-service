package com.unionclass.paymentservice.domain.order.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.unionclass.paymentservice.common.exception.BaseException;
import com.unionclass.paymentservice.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    PENDING("PENDING"),
    COMPLETED("COMPLETED"),
    CANCELED("CANCELED")
    ;

    private final String orderStatus;

    @JsonValue
    public String getStatus() { return orderStatus; }

    @JsonCreator
    public static OrderStatus fromString(String value) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.orderStatus.equals(value)) {
                return orderStatus;
            }
        }
        throw new BaseException(ErrorCode.INVALID_ORDER_STATUS_VALUE);
    }
}
