package com.unionclass.paymentservice.domain.payment.application;

import com.unionclass.paymentservice.domain.payment.dto.in.CreatePaymentCancelReqDto;

public interface PaymentCancelService {

    void createPaymentCancel(CreatePaymentCancelReqDto dto);
}
