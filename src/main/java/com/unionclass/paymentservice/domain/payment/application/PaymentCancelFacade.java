package com.unionclass.paymentservice.domain.payment.application;

import com.unionclass.paymentservice.domain.payment.dto.GetCancelsDto;
import com.unionclass.paymentservice.domain.payment.dto.in.CancelPaymentReqDto;

public interface PaymentCancelFacade {

    void cancelPayment(CancelPaymentReqDto cancelPaymentReqDto, GetCancelsDto getCancelsDto);
}
