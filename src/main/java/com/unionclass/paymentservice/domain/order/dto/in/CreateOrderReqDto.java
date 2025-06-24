package com.unionclass.paymentservice.domain.order.dto.in;

import com.unionclass.paymentservice.domain.order.entity.Orders;
import com.unionclass.paymentservice.domain.order.enums.OrderStatus;
import com.unionclass.paymentservice.domain.order.vo.in.CreateOrderReqVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@NoArgsConstructor
public class CreateOrderReqDto {

    private String memberUuid;
    private Long amount;
    private Long chargePoint;
    private Long bonusPoint;

    @Builder
    public CreateOrderReqDto(String memberUuid, Long amount, Long chargePoint, Long bonusPoint) {
        this.memberUuid = memberUuid;
        this.amount = amount;
        this.chargePoint = chargePoint;
        this.bonusPoint = bonusPoint;
    }

    public static CreateOrderReqDto of(String memberUuid, CreateOrderReqVo createOrderReqVo) {
        return CreateOrderReqDto.builder()
                .memberUuid(memberUuid)
                .amount(createOrderReqVo.getAmount())
                .chargePoint(createOrderReqVo.getChargePoint())
                .bonusPoint(createOrderReqVo.getBonusPoint())
                .build();
    }

    public Orders toEntity(String orderId, String orderName) {
        return Orders.builder()
                .id(orderId)
                .name(orderName)
                .memberUuid(memberUuid)
                .amount(amount)
                .chargePoint(chargePoint)
                .bonusPoint(bonusPoint)
                .totalPoint(calculateTotalPoint())
                .status(OrderStatus.PENDING)
                .build();
    }

    private long calculateTotalPoint() {
        return Optional.ofNullable(chargePoint).orElse(0L) + Optional.ofNullable(bonusPoint).orElse(0L);
    }
}
