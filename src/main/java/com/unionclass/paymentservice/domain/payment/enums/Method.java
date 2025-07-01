package com.unionclass.paymentservice.domain.payment.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.unionclass.paymentservice.common.exception.BaseException;
import com.unionclass.paymentservice.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Method {

    CARD("카드"),
    BANK_TRANSFER("계좌이체"),
    MOBILE("휴대폰"),
    VIRTUAL_ACCOUNT("가상계좌"),
    SIMPLE_PAYMENT("간편결제"),
    CULTURE_GIFT_CERTIFICATE("문화상품권"),
    BOOK_CULTURE_GIFT_CERTIFICATE("도서문화상품권"),
    GAME_CULTURE_GIFT_CERTIFICATE("게임문화상품권"),
    ;

    private final String method;

    @JsonValue
    public String getMethod() { return method; }

    @JsonCreator
    public static Method fromString(String value) {
        for (Method method : Method.values()) {
            if (method.method.equals(value)) {
                return method;
            }
        }
        throw new BaseException(ErrorCode.INVALID_PAYMENT_METHOD_VALUE);
    }
}
