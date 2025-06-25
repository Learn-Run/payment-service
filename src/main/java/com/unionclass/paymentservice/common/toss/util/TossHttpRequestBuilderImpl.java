package com.unionclass.paymentservice.common.toss.util;

import com.unionclass.paymentservice.common.config.TossPaymentConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class TossHttpRequestBuilderImpl implements TossHttpRequestBuilder {

    private final TossPaymentConfig config;

    @Override
    public HttpHeaders buildHeaders() { return config.getHeaders(); }

    @Override
    public HttpEntity<Map<String, Object>> buildEntity(Map<String, Object> body) {
        return new HttpEntity<>(body, buildHeaders());
    }

}
