package com.unionclass.paymentservice.domain.payment.application;

import com.unionclass.paymentservice.domain.payment.dto.in.CreateOrderAndRequestPaymentReqDto;
import com.unionclass.paymentservice.domain.payment.dto.out.RequestPaymentResDto;

public interface PaymentFacade {

    RequestPaymentResDto createOrderAndRequestPayment(CreateOrderAndRequestPaymentReqDto dto);
}
