package com.unionclass.paymentservice.domain.order.vo.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateOrderResVo {

    private String orderId;
    private String orderName;
    private Long amount;

    @Builder
    public CreateOrderResVo(String orderId, String orderName, Long amount) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.amount = amount;
    }
}
