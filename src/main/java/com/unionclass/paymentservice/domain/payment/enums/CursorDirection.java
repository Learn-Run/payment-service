package com.unionclass.paymentservice.domain.payment.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.unionclass.paymentservice.common.exception.BaseException;
import com.unionclass.paymentservice.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CursorDirection {

    NEXT("NEXT"),
    PREV("PREV"),
    ;

    public final String cursorDirection;

    @JsonValue
    public String getCursorDirection() { return cursorDirection; }

    public static CursorDirection fromString(String value) {
        for (CursorDirection cursorDirection : CursorDirection.values()) {
            if (cursorDirection.cursorDirection.equals(value)) {
                return cursorDirection;
            }
        }
        throw new BaseException(ErrorCode.INVALID_CURSOR_DIRECTION_VALUE);
    }
}
