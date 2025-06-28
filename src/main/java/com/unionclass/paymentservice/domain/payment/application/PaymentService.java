package com.unionclass.paymentservice.domain.payment.application;

import com.unionclass.paymentservice.domain.payment.dto.in.*;
import com.unionclass.paymentservice.domain.payment.dto.out.ConfirmPaymentResDto;
import com.unionclass.paymentservice.domain.payment.dto.out.GetPaymentDetailsResDto;
import com.unionclass.paymentservice.domain.payment.dto.out.GetPaymentSummaryResDto;
import com.unionclass.paymentservice.domain.payment.dto.out.RequestPaymentResDto;

public interface PaymentService {

    RequestPaymentResDto requestPayment(RequestPaymentReqDto dto);

    ConfirmPaymentResDto confirmPayment(ConfirmPaymentReqDto dto);

    void createPayment(CreatePaymentReqDto dto, String memberUuid);

    void cancelPayment(CancelPaymentReqDto dto);

    GetPaymentDetailsResDto getPaymentDetailsByPaymentKey(GetPaymentKeyReqDto dto);

    GetPaymentDetailsResDto getPaymentDetailsByOrderId(GetOrderIdReqDto dto);

    GetPaymentSummaryResDto getPaymentSummary(GetPaymentSummaryReqDto dto);
}
