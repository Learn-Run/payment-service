package com.unionclass.paymentservice.domain.payment.dto.in;

import com.unionclass.paymentservice.domain.payment.entity.Payment;
import com.unionclass.paymentservice.domain.payment.enums.PaymentMethod;
import com.unionclass.paymentservice.domain.payment.vo.in.CreatePaymentReqVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePaymentReqDto {

    private String memberUuid;
    private String orderId;
    private String orderName;
    private Long amount;
    private PaymentMethod paymentMethod;

    @Builder
    public CreatePaymentReqDto(String memberUuid, String orderId, String orderName, Long amount, PaymentMethod paymentMethod) {
        this.memberUuid = memberUuid;
        this.orderId = orderId;
        this.orderName = orderName;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    public static CreatePaymentReqDto of(String memberUuid, CreatePaymentReqVo createPaymentReqVo) {
        return CreatePaymentReqDto.builder()
                .memberUuid(memberUuid)
                .orderId(createPaymentReqVo.getOrderId())
                .orderName(createPaymentReqVo.getOrderName())
                .amount(createPaymentReqVo.getAmount())
                .paymentMethod(createPaymentReqVo.getPaymentMethod())
                .build();
    }
}
