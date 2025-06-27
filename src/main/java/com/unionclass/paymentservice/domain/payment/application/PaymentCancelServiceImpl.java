package com.unionclass.paymentservice.domain.payment.application;

import com.unionclass.paymentservice.common.exception.BaseException;
import com.unionclass.paymentservice.common.exception.ErrorCode;
import com.unionclass.paymentservice.common.util.NumericUuidGenerator;
import com.unionclass.paymentservice.domain.payment.dto.in.CreatePaymentCancelReqDto;
import com.unionclass.paymentservice.domain.payment.entity.PaymentCancel;
import com.unionclass.paymentservice.domain.payment.infrastructure.PaymentCancelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentCancelServiceImpl implements PaymentCancelService {

    private final PaymentCancelRepository paymentCancelRepository;

    private final NumericUuidGenerator uuidGenerator;

    @Transactional
    @Override
    public void createPaymentCancel(CreatePaymentCancelReqDto dto) {

        try {

            paymentCancelRepository.save(dto.toEntity(uuidGenerator.generate()));

            log.info("결제 취소 정보 저장 성공 - memberUuid: {}, paymentKey: {}, orderId: {}",
                    dto.getMemberUuid(), dto.getPaymentKey(), dto.getOrderId());

        } catch (Exception e) {

            log.warn("결제 취소 정보 저장 실패 - memberUuid: {}, paymentKey: {}, orderId: {}",
                    dto.getMemberUuid(), dto.getPaymentKey(), dto.getOrderId());

            throw new BaseException(ErrorCode.FAILED_TO_CREATE_PAYMENT_CANCEL);
        }
    }
}
