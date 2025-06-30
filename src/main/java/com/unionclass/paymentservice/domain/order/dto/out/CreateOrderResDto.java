package com.unionclass.paymentservice.domain.order.dto.out;

import com.unionclass.paymentservice.domain.order.entity.Orders;
import com.unionclass.paymentservice.domain.order.vo.out.CreateOrderResVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateOrderResDto {

    private String orderId;
    private String orderName;
    private Long amount;

    @Builder
    public CreateOrderResDto(String orderId, String orderName, Long amount) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.amount = amount;
    }

    public static CreateOrderResDto from(Orders orders) {
        return CreateOrderResDto.builder()
                .orderId(orders.getId())
                .orderName(orders.getName())
                .amount(orders.getAmount())
                .build();
    }

    public CreateOrderResVo toVo() {
        return CreateOrderResVo.builder()
                .orderId(orderId)
                .orderName(orderName)
                .amount(amount)
                .build();
    }
}
