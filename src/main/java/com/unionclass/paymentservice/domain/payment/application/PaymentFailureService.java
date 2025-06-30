package com.unionclass.paymentservice.domain.payment.application;

import com.unionclass.paymentservice.domain.payment.dto.in.RecordPaymentFailureReqDto;

public interface PaymentFailureService {

    void recordPaymentFailure(RecordPaymentFailureReqDto dto);
}
