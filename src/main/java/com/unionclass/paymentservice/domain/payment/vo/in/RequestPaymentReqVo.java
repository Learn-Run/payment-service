package com.unionclass.paymentservice.domain.payment.vo.in;

import com.unionclass.paymentservice.domain.payment.enums.Method;
import lombok.Getter;

@Getter
public class RequestPaymentReqVo {

    private String orderId;
    private String orderName;
    private Long amount;
    private Method paymentMethod;
}
