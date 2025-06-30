package com.unionclass.paymentservice.domain.payment.dto.in;

import com.unionclass.paymentservice.domain.payment.enums.Method;
import com.unionclass.paymentservice.domain.payment.vo.in.CreateOrderAndRequestPaymentReqVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateOrderAndRequestPaymentReqDto {

    private String memberUuid;
    private String orderName;
    private Long point;
    private Long bonusPoint;
    private Long paymentAmount;
    private Method paymentMethod;

    @Builder
    public CreateOrderAndRequestPaymentReqDto(
            String memberUuid, String orderName, Long point, Long bonusPoint, Long paymentAmount, Method paymentMethod
    ) {
        this.memberUuid = memberUuid;
        this.orderName = orderName;
        this.point = point;
        this.bonusPoint = bonusPoint;
        this.paymentAmount = paymentAmount;
        this.paymentMethod = paymentMethod;
    }

    public static CreateOrderAndRequestPaymentReqDto of(
            String memberUuid, CreateOrderAndRequestPaymentReqVo vo
    ) {
        return CreateOrderAndRequestPaymentReqDto.builder()
                .memberUuid(memberUuid)
                .orderName(vo.getOrderName())
                .point(vo.getPoint())
                .bonusPoint(vo.getBonusPoint())
                .paymentAmount(vo.getPaymentAmount())
                .paymentMethod(vo.getPaymentMethod())
                .build();
    }
}
