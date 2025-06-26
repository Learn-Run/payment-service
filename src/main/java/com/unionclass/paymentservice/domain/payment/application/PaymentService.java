package com.unionclass.paymentservice.domain.payment.application;

import com.unionclass.paymentservice.domain.payment.dto.in.*;
import com.unionclass.paymentservice.domain.payment.dto.out.GetPaymentDetailsResDto;
import com.unionclass.paymentservice.domain.payment.dto.out.RequestPaymentResDto;

public interface PaymentService {

    RequestPaymentResDto requestPayment(RequestPaymentReqDto dto);
    void confirmPayment(ConfirmPaymentReqDto dto);
    void createPayment(CreatePaymentReqDto dto, String memberUuid);
    void cancelPayment(CancelPaymentReqDto dto);
    GetPaymentDetailsResDto getPaymentDetailsByPaymentKey(GetPaymentDetailsReqDto dto);
}
