package com.unionclass.paymentservice.domain.payment.util;

import com.unionclass.paymentservice.domain.payment.dto.in.ConfirmPaymentReqDto;
import com.unionclass.paymentservice.domain.payment.dto.in.RequestPaymentReqDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import java.util.Map;

public interface TossHttpRequestBuilder {

    HttpHeaders buildHeaders();

    HttpEntity<Map<String, Object>> buildEntity(Map<String, Object> body);

    Map<String, Object> buildRequestPaymentPayload(RequestPaymentReqDto dto);

    Map<String, Object> buildConfirmPaymentPayload(ConfirmPaymentReqDto dto);
}
