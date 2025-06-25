package com.unionclass.paymentservice.domain.payment.vo.in;

import lombok.Getter;

@Getter
public class RequestPaymentReqVo {

    private String orderId;
    private String orderName;
    private Long amount;
    private PaymentMethod paymentMethod;
}
