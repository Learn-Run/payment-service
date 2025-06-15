package com.unionclass.paymentservice.common.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class NumericUuidGenerator {

    public Long generate() {
        return Math.abs(UUID.randomUUID().getMostSignificantBits());
    }
}