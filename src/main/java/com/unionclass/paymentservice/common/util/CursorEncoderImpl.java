package com.unionclass.paymentservice.common.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CursorEncoderImpl implements CursorEncoder {

    @Override
    public String encodeCursor(LocalDateTime createdAt, Long uuid) {

        return createdAt.toString() + "|" + uuid;
    }
}
