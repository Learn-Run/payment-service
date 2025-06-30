package com.unionclass.paymentservice.common.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StringUuidGenerator implements UuidGenerator<String> {

    @Override
    public String generate() { return UUID.randomUUID().toString(); }
}
