package com.unionclass.paymentservice.domain.payment.application;

import com.unionclass.paymentservice.common.response.CursorPage;
import com.unionclass.paymentservice.domain.payment.dto.in.*;
import com.unionclass.paymentservice.domain.payment.dto.out.*;

public interface PaymentService {

    RequestPaymentResDto requestPayment(RequestPaymentReqDto dto);

    void confirmPayment(ConfirmPaymentReqDto dto);

    void createPayment(CreatePaymentReqDto dto, String memberUuid);

    void cancelPayment(CancelPaymentReqDto dto);

    GetPaymentDetailsResDto getPaymentDetailsByPaymentKey(GetPaymentKeyReqDto dto);

    GetPaymentDetailsResDto getPaymentDetailsByOrderId(GetOrderIdReqDto dto);

    GetPaymentSummaryResDto getPaymentSummary(GetPaymentSummaryReqDto dto);

    CursorPage<GetPaymentUuidResDto> getAllPaymentUuids(CursorPageParamReqDto dto);
}
