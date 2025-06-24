package com.unionclass.paymentservice.domain.order.vo.in;

import lombok.Getter;

@Getter
public class CreateOrderReqVo {

    private Long amount;
    private Long chargePoint;
    private Long bonusPoint;
}
