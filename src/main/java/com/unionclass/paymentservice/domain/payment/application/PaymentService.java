package com.unionclass.paymentservice.domain.payment.application;

import com.unionclass.paymentservice.domain.payment.dto.in.ConfirmPaymentReqDto;
import com.unionclass.paymentservice.domain.payment.dto.in.CreatePaymentReqDto;
import com.unionclass.paymentservice.domain.payment.dto.out.CreatePaymentResDto;

public interface PaymentService {

    CreatePaymentResDto createPayment(CreatePaymentReqDto createPaymentReqDto);
    void confirmPayment(ConfirmPaymentReqDto confirmPaymentReqDto);
}
