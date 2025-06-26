package com.unionclass.paymentservice.domain.payment.util;

import com.unionclass.paymentservice.common.config.TossPaymentConfig;
import com.unionclass.paymentservice.domain.payment.dto.in.ConfirmPaymentReqDto;
import com.unionclass.paymentservice.domain.payment.dto.in.RequestPaymentReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;
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

    @Override
    public Map<String, Object> buildRequestPaymentPayload(RequestPaymentReqDto dto) {

        Map<String, Object> body = new HashMap<>();

        body.put("orderId", dto.getOrderId());
        body.put("orderName", dto.getOrderName());
        body.put("amount", dto.getAmount());
        body.put("method", dto.getMethod());
        body.put("successUrl", config.getSuccessUrl());
        body.put("failUrl", config.getFailUrl());
        body.put("validHours", 1);

        return body;
    }

    @Override
    public Map<String, Object> buildConfirmPaymentPayload(ConfirmPaymentReqDto dto) {

        Map<String, Object> body = new HashMap<>();

        body.put("paymentKey", dto.getPaymentKey());
        body.put("orderId", dto.getOrderId());
        body.put("amount", dto.getAmount());

        return body;
    }
}
