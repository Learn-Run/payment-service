package com.unionclass.paymentservice.common.util;

import java.time.LocalDateTime;

public interface CursorEncoder {

    String encodeCursor(LocalDateTime createdAt, Long uuid);
}
