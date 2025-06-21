package com.unionclass.paymentservice.domain.payment.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionclass.paymentservice.common.config.TossPaymentConfig;
import com.unionclass.paymentservice.common.exception.BaseException;
import com.unionclass.paymentservice.common.exception.ErrorCode;
import com.unionclass.paymentservice.common.util.NumericUuidGenerator;
import com.unionclass.paymentservice.domain.payment.dto.in.CancelPaymentReqDto;
import com.unionclass.paymentservice.domain.payment.dto.in.ConfirmPaymentReqDto;
import com.unionclass.paymentservice.domain.payment.dto.in.CreatePaymentReqDto;
import com.unionclass.paymentservice.domain.payment.dto.out.CreatePaymentResDto;
import com.unionclass.paymentservice.domain.payment.entity.Payment;
import com.unionclass.paymentservice.domain.payment.infrastructure.PaymentRepository;
import com.unionclass.paymentservice.domain.payment.infrastructure.RefundHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final RefundHistoryRepository refundHistoryRepository;
    private final TossPaymentConfig tossPaymentConfig;
    private final NumericUuidGenerator numericUuidGenerator;
    private final RestTemplate restTemplate;

    @Transactional
    @Override
    public CreatePaymentResDto createPayment(CreatePaymentReqDto createPaymentReqDto) {

        HttpHeaders httpHeaders = tossPaymentConfig.getHeaders();

        Map<String, Object> body = new HashMap<>();
        body.put("orderId", createPaymentReqDto.getOrderId());
        body.put("orderName", createPaymentReqDto.getOrderName());
        body.put("amount", createPaymentReqDto.getAmount());
        body.put("method", createPaymentReqDto.getPaymentMethod());
        body.put("successUrl", tossPaymentConfig.getSuccessUrl());
        body.put("failUrl", tossPaymentConfig.getFailUrl());
        body.put("validHours", 1);

        HttpEntity<Map<String, Object>> httpRequest = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                tossPaymentConfig.getBaseUrl(), httpRequest, Map.class);

        Map responseBody = response.getBody();

        Object checkoutObject =responseBody.get("checkout");

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> checkout = objectMapper.convertValue(checkoutObject, new TypeReference<>() {});

        if (checkout == null || !checkout.containsKey("url")) {
            throw new BaseException(ErrorCode.TOSS_EMPTY_RESPONSE);
        }
        String checkoutUrl = checkout.get("url").toString();

        return CreatePaymentResDto.of(createPaymentReqDto.getOrderId(), checkoutUrl);
    }

    @Transactional
    @Override
    public void confirmPayment(ConfirmPaymentReqDto confirmPaymentReqDto) {
        try {
            HttpHeaders httpHeaders = tossPaymentConfig.getHeaders();

            Map<String, Object> body = new HashMap<>();
            body.put("paymentKey", confirmPaymentReqDto.getPaymentKey());
            body.put("orderId", confirmPaymentReqDto.getOrderId());
            body.put("amount", confirmPaymentReqDto.getAmount());

            HttpEntity<Map<String, Object>> httpRequest = new HttpEntity<>(body, httpHeaders);

            Payment payment = confirmPaymentReqDto.toEntity(numericUuidGenerator.generate());
            paymentRepository.save(payment);

            ResponseEntity<Map> response = restTemplate.postForEntity(
                    tossPaymentConfig.getBaseUrl() + "/confirm", httpRequest, Map.class);

            Map responseBody = response.getBody();

            if (responseBody == null) {
                throw new BaseException(ErrorCode.TOSS_EMPTY_RESPONSE);
            }

            Map<String, Object> failure = (Map<String, Object>) responseBody.get("failure");

            LocalDateTime approvedAt = Optional.ofNullable(responseBody.get("approvedAt").toString())
                    .map(OffsetDateTime::parse)
                    .map(OffsetDateTime::toLocalDateTime)
                    .orElse(null);

            if (failure != null) {

                payment.recordFail(failure.get("code").toString(), failure.get("message").toString());
                throw new BaseException(ErrorCode.TOSS_PAYMENT_REJECTED);
            }

            payment.approvePayment(approvedAt);
            paymentRepository.save(payment);

        } catch (HttpClientErrorException e) {
            log.warn("결제 승인 시도 중 오류 발생 - paymentKey: {}, message: {}",
                    confirmPaymentReqDto.getPaymentKey(), e.getMessage(), e);
            throw new BaseException(ErrorCode.TOSS_API_CALL_FAILED);
        }
    }

    @Transactional
    @Override
    public void cancelPayment(CancelPaymentReqDto cancelPaymentReqDto) {

        try {
            Payment payment = paymentRepository.findByPaymentKey(cancelPaymentReqDto.getPaymentKey())
                    .orElseThrow(() -> new BaseException(ErrorCode.FAILED_TO_FIND_PAYMENT_BY_PAYMENT_KEY));

            payment.cancel();
            refundHistoryRepository.save(cancelPaymentReqDto.toEntity(numericUuidGenerator.generate(), payment));
        } catch (Exception e) {
            throw new BaseException(ErrorCode.FAILED_TO_SAVE_PAYMENT_AND_REFUND_HISTORY);
        }

        HttpHeaders httpHeaders = tossPaymentConfig.getHeaders();

        Map<String, Object> body = new HashMap<>();
        body.put("cancelReason", cancelPaymentReqDto.getCancelReason());

        HttpEntity<Map<String, Object>> httpRequest = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                tossPaymentConfig.getBaseUrl() + "/{paymentKey}/cancel", httpRequest, Map.class, cancelPaymentReqDto.getPaymentKey());

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("toss 와의 통신 성공 및 환불 처리 완료 - paymentKey: {}", cancelPaymentReqDto.getPaymentKey());
        } else {
            throw new BaseException(ErrorCode.FAILED_TO_CALL_TOSS_API_FOR_REFUND);
        }
    }
}