package com.unionclass.paymentservice.domain.payment.infrastructure.adapter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.unionclass.paymentservice.common.config.TossPaymentConfig;
import com.unionclass.paymentservice.common.exception.BaseException;
import com.unionclass.paymentservice.common.exception.ErrorCode;
import com.unionclass.paymentservice.common.util.JsonMapper;
import com.unionclass.paymentservice.domain.payment.application.port.PaymentGateway;
import com.unionclass.paymentservice.domain.payment.dto.in.CancelPaymentReqDto;
import com.unionclass.paymentservice.domain.payment.dto.in.ConfirmPaymentReqDto;
import com.unionclass.paymentservice.domain.payment.dto.in.RequestPaymentReqDto;
import com.unionclass.paymentservice.domain.payment.dto.out.GetPaymentDetailsResDto;
import com.unionclass.paymentservice.domain.payment.util.TossHttpRequestBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class TossPaymentGateway implements PaymentGateway {

    private final TossPaymentConfig tossPaymentConfig;
    private final RestTemplate restTemplate;
    private final TossHttpRequestBuilder httpRequestBuilder;
    private final JsonMapper jsonMapper;

    @Override
    public String requestPayment(RequestPaymentReqDto dto) {

        try {

            Map<String, Object> response = restTemplate.exchange(
                    tossPaymentConfig.getBaseUrl(),
                    HttpMethod.POST,
                    httpRequestBuilder.buildEntity(httpRequestBuilder.buildRequestPaymentPayload(dto)),
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    }
            ).getBody();

            Map<String, Object> checkout = jsonMapper.convert(
                    Objects.requireNonNull(response).get("checkout"),
                    new TypeReference<Map<String, Object>>() {
                    }
            );

            return checkout.get("url").toString();

        } catch (Exception e) {

            log.warn("결제 요청 실패 - memberUuid: {}, orderId: {}, message: {}",
                    dto.getMemberUuid(), dto.getOrderId(), e.getMessage(), e);

            throw new BaseException(ErrorCode.FAILED_TO_REQUEST_PAYMENT);
        }
    }

    @Override
    public Object confirmPayment(ConfirmPaymentReqDto dto) {

        try {

            return restTemplate.exchange(
                    tossPaymentConfig.getBaseUrl() + "/confirm",
                    HttpMethod.POST,
                    httpRequestBuilder.buildEntity(httpRequestBuilder.buildConfirmPaymentPayload(dto)),
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    }
            ).getBody();

        } catch (HttpClientErrorException e) {

            log.warn("결제 승인 실패 - paymentKey: {}, message: {}", dto.getPaymentKey(), e.getMessage(), e);

            throw e;

        } catch (Exception e) {

            log.warn("결제 승인 중 알 수 없는 오류 발생 - paymentKey: {}, message: {}", dto.getPaymentKey(), e.getMessage(), e);

            throw new BaseException(ErrorCode.FAILED_TO_CONFIRM_PAYMENT);
        }
    }

    @Override
    public Object cancelPayment(CancelPaymentReqDto dto) {

        try {

            Map<String, Object> response = restTemplate.exchange(
                    tossPaymentConfig.getBaseUrl() + "/" + dto.getPaymentKey() + "/cancel",
                    HttpMethod.POST,
                    httpRequestBuilder.buildEntity(httpRequestBuilder.buildCancelPaymentPayload(dto)),
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    }
            ).getBody();

            return Objects.requireNonNull(response).get("cancels");

        } catch (Exception e) {

            log.warn("결제 취소 중 알 수 없는 오류 발생 - paymentKey: {}, message: {}",
                    dto.getPaymentKey(), e.getMessage(), e);

            throw new BaseException(ErrorCode.FAILED_TO_CANCEL_PAYMENT);
        }
    }

    @Override
    public GetPaymentDetailsResDto getPaymentDetailsByPaymentKey(String paymentKey) {

        try {

            return jsonMapper.convert(
                    restTemplate.exchange(
                            tossPaymentConfig.getBaseUrl() + "/" + paymentKey,
                            HttpMethod.GET,
                            new HttpEntity<>(httpRequestBuilder.buildHeaders()),
                            new ParameterizedTypeReference<Map<String, Object>>() {
                            }
                    ).getBody(), GetPaymentDetailsResDto.class);

        } catch (HttpClientErrorException e) {

            log.warn("toss 로 부터 결제 상세 정보 단건 조회 실패 - message: {}", e.getMessage(), e);

            throw e;

        } catch (Exception e) {

            log.warn("결제 상세 정보 단건 조회 실패 - paymentKey: {}, message: {}", paymentKey, e.getMessage(), e);

            throw new BaseException(ErrorCode.FAILED_TO_GET_PAYMENT_DETAILS);
        }
    }

    @Override
    public GetPaymentDetailsResDto getPaymentDetailsByOrderId(String orderId) {

        try {

            return jsonMapper.convert(
                    restTemplate.exchange(
                            tossPaymentConfig.getBaseUrl() + "/orders" + orderId,
                            HttpMethod.GET,
                            new HttpEntity<>(httpRequestBuilder.buildHeaders()),
                            new ParameterizedTypeReference<Map<String, Object>>() {
                            }
                    ).getBody(), GetPaymentDetailsResDto.class);

        } catch (HttpClientErrorException e) {

            log.warn("toss 로 부터 결제 상세 정보 단건 조회 실패 - message: {}", e.getMessage(), e);

            throw e;

        } catch (Exception e) {

            log.warn("결제 상세 정보 단건 조회 실패 - orderId: {}, message: {}", orderId, e.getMessage(), e);

            throw new BaseException(ErrorCode.FAILED_TO_GET_PAYMENT_DETAILS);
        }
    }
} 