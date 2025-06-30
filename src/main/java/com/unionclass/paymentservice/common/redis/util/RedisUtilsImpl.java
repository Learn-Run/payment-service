package com.unionclass.paymentservice.common.redis.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtilsImpl implements RedisUtils {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * RedisUtils
     *
     * 1. 해당 키에 값을 저장하고, TTL 을 설정
     * 2. 해당 키의 Value 값을 조회
     * 3. 해당 키 삭제
     * 4. 해당 키 존재 여부 확인
     */

    /**
     * 1. 해당 키에 값을 저장하고, TTL 을 설정
     * @param key
     * @param value
     * @param ttlSeconds
     * @param timeUnit
     */
    @Override
    public void setValueWithTTL(String key, Object value, long ttlSeconds, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, ttlSeconds, timeUnit);
    }

    /**
     * 2. 해당 키의 Value 값을 조회
     * @param key
     * @return
     */
    @Override
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 3. 해당 키 삭제
     * @param key
     */
    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 4. 해당 키 존재 여부 확인
     * @param key
     * @return
     */
    @Override
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}