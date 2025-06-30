package com.unionclass.paymentservice.domain.payment.dto.in;

import com.unionclass.paymentservice.common.exception.BaseException;
import com.unionclass.paymentservice.common.exception.ErrorCode;
import com.unionclass.paymentservice.domain.payment.enums.CursorDirection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@NoArgsConstructor
public class CursorPageParamReqDto {

    private String memberUuid;
    private String createdAtCursor;
    private Long uuidCursor;
    private CursorDirection direction;
    private int size;

    @Builder
    public CursorPageParamReqDto(
            String memberUuid, String createdAtCursor, Long uuidCursor, CursorDirection direction, int size) {
        this.memberUuid = memberUuid;
        this.createdAtCursor = createdAtCursor;
        this.uuidCursor = uuidCursor;
        this.direction = direction;
        this.size = size;
    }

    public static CursorPageParamReqDto of(String memberUuid, String cursor, String direction, int size) {

        String createdAtCursor = null;
        Long uuidCursor = null;

        if (cursor != null && cursor.contains("|")) {

            String[] parts = cursor.split("\\|", 2);
            createdAtCursor = parts[0];

            try {
                uuidCursor = Long.parseLong(parts[1]);

            } catch (NumberFormatException e) {

                log.warn("Cursor 파싱 실패 - uuidCursor 가 Long 타입이 아닙니다. - 입력값: {}, message: {}",
                        parts[1], e.getMessage(), e);

                throw new BaseException(ErrorCode.INVALID_CURSOR_FORMAT);
            }
        }

        return CursorPageParamReqDto.builder()
                .memberUuid(memberUuid)
                .createdAtCursor(createdAtCursor)
                .uuidCursor(uuidCursor)
                .direction(CursorDirection.fromString(direction))
                .size(size)
                .build();
    }
}
