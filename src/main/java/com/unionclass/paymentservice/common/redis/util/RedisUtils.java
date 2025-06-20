package com.unionclass.paymentservice.common.redis.util;

import java.util.concurrent.TimeUnit;

public interface RedisUtils {

    /**
     * RedisUtils
     *
     * 1. 해당 키에 값을 저장하고, TTL 을 설정
     * 2. 해당 키의 Value 값을 조회
     * 3. 해당 키 삭제
     * 4. 해당 키 존재 여부 확인
     */

    void setValueWithTTL(String key, Object value, long ttlSeconds, TimeUnit timeUnit);
    Object getValue(String key);
    void delete(String key);
    boolean hasKey(String key);
}
