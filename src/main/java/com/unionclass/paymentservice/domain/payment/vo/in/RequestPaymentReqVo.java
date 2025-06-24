package com.unionclass.paymentservice.domain.payment.vo.in;

import com.unionclass.paymentservice.domain.payment.enums.PaymentMethod;
import lombok.Getter;

@Getter
public class RequestPaymentReqVo {

    private String orderId;
    private String orderName;
    private Long amount;
    private PaymentMethod paymentMethod;
}
