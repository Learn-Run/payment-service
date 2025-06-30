package com.unionclass.paymentservice.common.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;

@Getter
@Configuration
public class TossPaymentConfig {

    @Value("${payment.toss.client_key}")
    private String clientKey;

    @Value("${payment.toss.secret_key}")
    private String secretKey;

    @Value("${payment.toss.base_url}")
    private String baseUrl;

    @Value("${payment.toss.success_url}")
    private String successUrl;

    @Value("${payment.toss.fail_url}")
    private String failUrl;

    @Bean
    public HttpHeaders getHeaders() {

        HttpHeaders httpHeaders = new HttpHeaders();
        String credentials = secretKey + ":";
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        httpHeaders.set("Authorization", "Basic " + encodedCredentials);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }
}