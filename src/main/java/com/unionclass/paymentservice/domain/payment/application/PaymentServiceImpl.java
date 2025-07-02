package com.unionclass.paymentservice.domain.payment.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.unionclass.paymentservice.common.config.TossPaymentConfig;
import com.unionclass.paymentservice.common.exception.BaseException;
import com.unionclass.paymentservice.common.exception.ErrorCode;
import com.unionclass.paymentservice.common.response.CursorPage;
import com.unionclass.paymentservice.common.util.JsonMapper;
import com.unionclass.paymentservice.common.util.NumericUuidGenerator;
import com.unionclass.paymentservice.domain.payment.dto.CancelsDto;
import com.unionclass.paymentservice.domain.payment.dto.FailureDto;
import com.unionclass.paymentservice.domain.payment.dto.in.*;
import com.unionclass.paymentservice.domain.payment.dto.out.*;
import com.unionclass.paymentservice.domain.payment.infrastructure.PaymentRepository;
import com.unionclass.paymentservice.domain.payment.util.TossHttpRequestBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
    private final PaymentCancelFacade paymentCancelFacade;
    private final JsonMapper jsonMapper;

    private final TossPaymentConfig tossPaymentConfig;
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
    public void confirmPayment(ConfirmPaymentReqDto dto) {

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

//            return ConfirmPaymentResDto.of(200, ResponseMessage.SUCCESS_CONFIRM_PAYMENT.getMessage());

        } catch (HttpClientErrorException e) {

            FailureDto failure = jsonMapper.readValue(
                    e.getResponseBodyAsString(), FailureDto.class
            );

            paymentFailureService.recordPaymentFailure(RecordPaymentFailureReqDto.of(dto, failure));

            log.info("결제 승인 실패 - paymentKey: {}, message: {}", dto.getPaymentKey(), e.getMessage(), e);

            throw new BaseException(ErrorCode.FAILED_TO_CONFIRM_PAYMENT);
//            return ConfirmPaymentResDto.of(failure);

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

            paymentCancelFacade.cancelPayment(
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
                            CancelsDto.class
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
    public GetPaymentDetailsResDto getPaymentDetailsByPaymentKey(GetPaymentKeyReqDto dto) {

        try {

            return jsonMapper.convert(
                    restTemplate.exchange(
                            tossPaymentConfig.getBaseUrl() + "/" + dto.getPaymentKey(),
                            HttpMethod.GET,
                            new HttpEntity<>(httpRequestBuilder.buildHeaders()),
                            new ParameterizedTypeReference<Map<String, Object>>() {
                            }
                    ).getBody(), GetPaymentDetailsResDto.class);

        } catch (HttpClientErrorException e) {

            log.warn("toss 로 부터 결제 상세 정보 단건 조회 실패 - message: {}", e.getMessage(), e);

            throw e;

        } catch (Exception e) {

            log.warn("결제 상세 정보 단건 조회 실패 - paymentKey: {}, message: {}", dto.getPaymentKey(), e.getMessage(), e);

            throw new BaseException(ErrorCode.FAILED_TO_GET_PAYMENT_DETAILS);
        }
    }

    @Override
    public GetPaymentDetailsResDto getPaymentDetailsByOrderId(GetOrderIdReqDto dto) {

        try {

            return jsonMapper.convert(
                    restTemplate.exchange(
                            tossPaymentConfig.getBaseUrl() + "/orders" + dto.getOrderId(),
                            HttpMethod.GET,
                            new HttpEntity<>(httpRequestBuilder.buildHeaders()),
                            new ParameterizedTypeReference<Map<String, Object>>() {
                            }
                    ).getBody(), GetPaymentDetailsResDto.class);

        } catch (HttpClientErrorException e) {

            log.warn("toss 로 부터 결제 상세 정보 단건 조회 실패 - message: {}", e.getMessage(), e);

            throw e;

        } catch (Exception e) {

            log.warn("결제 상세 정보 단건 조회 실패 - orderId: {}, message: {}", dto.getOrderId(), e.getMessage(), e);

            throw new BaseException(ErrorCode.FAILED_TO_GET_PAYMENT_DETAILS);
        }
    }

    @Override
    public GetPaymentSummaryResDto getPaymentSummary(GetPaymentSummaryReqDto dto) {

        try {

            return GetPaymentSummaryResDto.from(
                    paymentRepository
                            .findByUuid(dto.getPaymentUuid())
                            .orElseThrow(() -> new BaseException(ErrorCode.FAILED_TO_FIND_PAYMENT_BY_PAYMENT_UUID))
            );

        } catch (Exception e) {

            log.warn("결제 요약 정보 단건 조회 실패 - memberUuid: {}, paymentUuid: {}, message {}",
                    dto.getMemberUuid(), dto.getPaymentUuid(), e.getMessage(), e);

            throw new BaseException(ErrorCode.FAILED_TO_GET_PAYMENT_SUMMARY);
        }
    }

    @Override
    public CursorPage<GetPaymentInfoResDto> getAllPaymentUuids(CursorPageParamReqDto dto) {

        try {

            return paymentRepository.findPaymentUuidsByCursor(dto);

        } catch (Exception e) {

            log.warn("결제 UUID 리스트 조회 실패 - memberUuid: {}, message {}", dto.getMemberUuid(), e.getMessage(), e);

            throw new BaseException(ErrorCode.FAILED_TO_GET_PAYMENT_UUID_LIST);
        }
    }
}