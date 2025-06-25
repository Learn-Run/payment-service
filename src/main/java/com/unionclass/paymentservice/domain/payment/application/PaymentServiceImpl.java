package com.unionclass.paymentservice.domain.payment.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.unionclass.paymentservice.common.config.TossPaymentConfig;
import com.unionclass.paymentservice.common.exception.BaseException;
import com.unionclass.paymentservice.common.exception.ErrorCode;
import com.unionclass.paymentservice.common.util.JsonMapper;
import com.unionclass.paymentservice.domain.payment.dto.in.*;
import com.unionclass.paymentservice.domain.payment.util.TossHttpRequestBuilder;
import com.unionclass.paymentservice.common.util.NumericUuidGenerator;
import com.unionclass.paymentservice.domain.payment.dto.out.GetPaymentDetailsResDto;
import com.unionclass.paymentservice.domain.payment.dto.out.RequestPaymentResDto;
import com.unionclass.paymentservice.domain.payment.entity.Payment;
import com.unionclass.paymentservice.domain.payment.infrastructure.PaymentRepository;
import com.unionclass.paymentservice.domain.payment.infrastructure.RefundHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.HashMap;
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
    private final TossHttpRequestBuilder httpRequestBuilder;
    private final JsonMapper jsonMapper;

    @Transactional
    @Override
    public RequestPaymentResDto requestPayment(RequestPaymentReqDto dto) {

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                tossPaymentConfig.getBaseUrl(),
                HttpMethod.POST,
                httpRequestBuilder.buildEntity(httpRequestBuilder.buildRequestPaymentPayload(dto)),
                new ParameterizedTypeReference<>() {}
        );

        Map<String, Object> responseBody = response.getBody();

        jsonMapper.convert(responseBody, CreatePaymentReqDto.class);

        return jsonMapper.convert(responseBody, RequestPaymentResDto.class);
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

    @Override
    public GetPaymentDetailsResDto getPaymentDetailsByPaymentKey(GetPaymentDetailsReqDto dto) {

        ResponseEntity<Map> response = restTemplate.exchange(
                tossPaymentConfig.getBaseUrl() + "/" + dto.getPaymentKey(),
                HttpMethod.GET,
                new HttpEntity<>(httpRequestBuilder.buildHeaders()),
                Map.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {

            log.warn("결제 상세 정보 단건 조회 실패 - toss 와의 통신 실패");

            throw new BaseException(ErrorCode.FAILED_TO_FIND_PAYMENT_DETAILS_BY_PAYMENT_KEY);

        }

        Map<String, Object> responseBody = response.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new ParameterNamesModule());
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper.convertValue(responseBody, GetPaymentDetailsResDto.class);
    }
}