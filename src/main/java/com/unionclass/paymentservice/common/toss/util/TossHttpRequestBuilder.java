package com.unionclass.paymentservice.common.toss.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import java.util.Map;

public interface TossHttpRequestBuilder {

    HttpHeaders buildHeaders();

    HttpEntity<Map<String, Object>> buildEntity(Map<String, Object> body);

}
