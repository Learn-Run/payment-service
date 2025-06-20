package com.unionclass.paymentservice.domain.payment.vo.in;

import com.unionclass.paymentservice.domain.payment.enums.PaymentMethod;
import lombok.Getter;

@Getter
public class ConfirmPaymentReqVo {

    private String paymentKey;
    private String orderId;
    private String orderName;
    private PaymentMethod paymentMethod;
    private Long amount;
}
