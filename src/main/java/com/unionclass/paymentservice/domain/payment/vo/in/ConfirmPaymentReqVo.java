package com.unionclass.paymentservice.domain.payment.vo.in;

import lombok.Getter;

@Getter
public class ConfirmPaymentReqVo {

    private String paymentKey;
    private String orderId;
    private Long amount;
}
