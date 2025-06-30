package com.unionclass.paymentservice.domain.payment.vo.in;

import com.unionclass.paymentservice.domain.payment.enums.Method;
import lombok.Getter;

@Getter
public class CreateOrderAndRequestPaymentReqVo {

    private String orderName;
    private Long point;
    private Long bonusPoint;
    private Long paymentAmount;
    private Method paymentMethod;
}
