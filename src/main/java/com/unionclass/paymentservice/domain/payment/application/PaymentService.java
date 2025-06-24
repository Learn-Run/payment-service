package com.unionclass.paymentservice.domain.payment.application;

import com.unionclass.paymentservice.domain.payment.dto.in.CancelPaymentReqDto;
import com.unionclass.paymentservice.domain.payment.dto.in.ConfirmPaymentReqDto;
import com.unionclass.paymentservice.domain.payment.dto.in.RequestPaymentReqDto;
import com.unionclass.paymentservice.domain.payment.dto.out.RequestPaymentResDto;

public interface PaymentService {

    RequestPaymentResDto requestPayment(RequestPaymentReqDto requestPaymentReqDto);
    void confirmPayment(ConfirmPaymentReqDto confirmPaymentReqDto);
    void cancelPayment(CancelPaymentReqDto cancelPaymentReqDto);
}
