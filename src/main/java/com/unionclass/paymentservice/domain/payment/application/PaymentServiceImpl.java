package com.unionclass.paymentservice.domain.payment.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.unionclass.paymentservice.common.config.TossPaymentConfig;
import com.unionclass.paymentservice.common.exception.BaseException;
import com.unionclass.paymentservice.common.exception.ErrorCode;
import com.unionclass.paymentservice.common.response.ResponseMessage;
import com.unionclass.paymentservice.common.util.JsonMapper;
import com.unionclass.paymentservice.common.util.NumericUuidGenerator;
import com.unionclass.paymentservice.domain.order.application.OrderService;
import com.unionclass.paymentservice.domain.payment.dto.GetCancelsDto;
import com.unionclass.paymentservice.domain.payment.dto.GetFailureDto;
import com.unionclass.paymentservice.domain.payment.dto.in.*;
import com.unionclass.paymentservice.domain.payment.dto.out.ConfirmPaymentResDto;
import com.unionclass.paymentservice.domain.payment.dto.out.GetPaymentDetailsResDto;
import com.unionclass.paymentservice.domain.payment.dto.out.RequestPaymentResDto;
import com.unionclass.paymentservice.domain.payment.infrastructure.PaymentRepository;
import com.unionclass.paymentservice.domain.payment.util.TossHttpRequestBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final TossHttpRequestBuilder httpRequestBuilder;
    private final PaymentFailureService paymentFailureService;
    private final PaymentCancelService paymentCancelService;
    private final PaymentCancelFacade paymentCancelFacade;
    private final OrderService orderService;
    private final JsonMapper jsonMapper;

    private final TossPaymentConfig tossPaymentConfig;
    private final NumericUuidGenerator numericUuidGenerator;
    private final RestTemplate restTemplate;
    private final NumericUuidGenerator uuidGenerator;

    @Transactional
    @Override
    public RequestPaymentResDto requestPayment(RequestPaymentReqDto dto) {

        try {

            return RequestPaymentResDto.from(
                    jsonMapper.convert(
                                    Objects.requireNonNull(
                                            restTemplate.exchange(
                                                    tossPaymentConfig.getBaseUrl(),
                                                    HttpMethod.POST,
                                                    httpRequestBuilder.buildEntity(httpRequestBuilder.buildRequestPaymentPayload(dto)),
                                                    new ParameterizedTypeReference<Map<String, Object>>() {
                                                    }
                                            ).getBody()
                                    ).get("checkout"), new TypeReference<Map<String, Object>>() {
                                    })
                            .get("url")
                            .toString());

        } catch (Exception e) {

            log.warn("결제 요청 실패 - memberUuid: {}, orderId: {}, message: {}",
                    dto.getMemberUuid(), dto.getOrderId(), e.getMessage(), e);

            throw new BaseException(ErrorCode.FAILED_TO_REQUEST_PAYMENT);
        }
    }

    @Transactional
    @Override
    public ConfirmPaymentResDto confirmPayment(ConfirmPaymentReqDto dto) {

        try {

            createPayment(
                    jsonMapper.convert(
                            restTemplate.exchange(
                                    tossPaymentConfig.getBaseUrl() + "/confirm",
                                    HttpMethod.POST,
                                    httpRequestBuilder.buildEntity(httpRequestBuilder.buildConfirmPaymentPayload(dto)),
                                    new ParameterizedTypeReference<Map<String, Object>>() {
                                    }
                            ).getBody(), CreatePaymentReqDto.class
                    ), dto.getMemberUuid()
            );

            log.info("결제 승인 성공 - paymentKey: {}", dto.getPaymentKey());

            return ConfirmPaymentResDto.of(200, ResponseMessage.SUCCESS_CONFIRM_PAYMENT.getMessage());

        } catch (HttpClientErrorException e) {

            GetFailureDto failure = jsonMapper.readValue(
                    e.getResponseBodyAsString(), GetFailureDto.class
            );

            paymentFailureService.recordPaymentFailure(RecordPaymentFailureReqDto.of(dto, failure));

            log.info("결제 승인 실패 - paymentKey: {}, message: {}", dto.getPaymentKey(), e.getMessage(), e);

            return ConfirmPaymentResDto.of(failure);

        } catch (Exception e) {

            log.warn("결제 승인 중 알 수 없는 오류 발생 - paymentKey: {}, message: {}", dto.getPaymentKey(), e.getMessage(), e);

            throw new BaseException(ErrorCode.FAILED_TO_CONFIRM_PAYMENT);
        }
    }

    @Transactional
    @Override
    public void createPayment(CreatePaymentReqDto dto, String memberUuid) {

        try {

            paymentRepository.save(dto.toEntity(uuidGenerator.generate(), memberUuid));

            log.info("결제 생성 성공 - memberUuid: {}, paymentKey: {}, orderId: {}",
                    memberUuid, dto.getPaymentKey(), dto.getOrderId());

        } catch (Exception e) {

            log.warn("결제 생성 실패 - memberUuid: {}, paymentKey: {}, orderId: {}",
                    memberUuid, dto.getPaymentKey(), dto.getOrderId());

            throw new BaseException(ErrorCode.FAILED_TO_CREATE_PAYMENT);
        }
    }

    @Transactional
    @Override
    public void cancelPayment(CancelPaymentReqDto dto) {

        try {

            paymentCancelFacade.cancelAndUpdate(
                    dto,
                    jsonMapper.convert(
                            Objects.requireNonNull(
                                    restTemplate.exchange(
                                            tossPaymentConfig.getBaseUrl() + "/" + dto.getPaymentKey() + "/cancel",
                                            HttpMethod.POST,
                                            httpRequestBuilder.buildEntity(httpRequestBuilder.buildCancelPaymentPayload(dto)),
                                            new ParameterizedTypeReference<Map<String, Object>>() {
                                            }
                                    ).getBody()
                            ).get("cancels"),
                            GetCancelsDto.class
                    )
            );

            log.info("결제 취소 성공 - paymentKey: {}", dto.getPaymentKey());

        } catch (Exception e) {

            log.warn("결제 취소 중 알 수 없는 오류 발생 - paymentKey: {}, message: {}",
                    dto.getPaymentKey(), e.getMessage(), e);

            throw new BaseException(ErrorCode.FAILED_TO_CANCEL_PAYMENT);
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