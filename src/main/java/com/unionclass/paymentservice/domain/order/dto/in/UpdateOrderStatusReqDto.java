package com.unionclass.paymentservice.domain.order.dto.in;

import com.unionclass.paymentservice.domain.order.entity.Orders;
import com.unionclass.paymentservice.domain.order.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateOrderStatusReqDto {

    private String orderId;
    private OrderStatus orderStatus;

    @Builder
    public UpdateOrderStatusReqDto(String orderId, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
    }

    public static UpdateOrderStatusReqDto from(String orderId) {

        return UpdateOrderStatusReqDto.builder()
                .orderId(orderId)
                .orderStatus(OrderStatus.CANCELED)
                .build();
    }

    public Orders toEntity(Orders order) {
        return Orders.builder()
                .id(order.getId())
                .name(order.getName())
                .memberUuid(order.getMemberUuid())
                .amount(order.getAmount())
                .chargePoint(order.getChargePoint())
                .bonusPoint(order.getBonusPoint())
                .totalPoint(order.getTotalPoint())
                .status(orderStatus)
                .build();
    }
}
