package com.unionclass.paymentservice.domain.payment.application;

import com.unionclass.paymentservice.domain.payment.dto.CancelsDto;
import com.unionclass.paymentservice.domain.payment.dto.in.CancelPaymentReqDto;

public interface PaymentCancelFacade {

    void cancelPayment(CancelPaymentReqDto cancelPaymentReqDto, CancelsDto cancelsDto);
}
