package com.unionclass.paymentservice.domain.payment.application;

import com.unionclass.paymentservice.common.exception.BaseException;
import com.unionclass.paymentservice.common.exception.ErrorCode;
import com.unionclass.paymentservice.common.util.NumericUuidGenerator;
import com.unionclass.paymentservice.domain.payment.dto.in.RecordPaymentFailureReqDto;
import com.unionclass.paymentservice.domain.payment.infrastructure.PaymentFailureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentFailureServiceImpl implements PaymentFailureService {

    private final PaymentFailureRepository paymentFailureRepository;

    private final NumericUuidGenerator uuidGenerator;

    @Transactional
    @Override
    public void recordPaymentFailure(RecordPaymentFailureReqDto dto) {

        try {

            paymentFailureRepository.save(dto.toEntity(uuidGenerator.generate()));

            log.info("결제 실패 정보 저장 성공 - memberUuid: {}, paymentKey: {}, orderId: {}",
                    dto.getMemberUuid(), dto.getPaymentKey(), dto.getOrderId());

        } catch (Exception e) {

            log.warn("결제 실패 정보 저장 성공 - memberUuid: {}, paymentKey: {}, orderId: {}",
                    dto.getMemberUuid(), dto.getPaymentKey(), dto.getOrderId());

            throw new BaseException(ErrorCode.FAILED_TO_RECORD_PAYMENT_FAILURE);
        }
    }
}
