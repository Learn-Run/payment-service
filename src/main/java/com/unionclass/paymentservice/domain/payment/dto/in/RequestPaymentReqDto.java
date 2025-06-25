package com.unionclass.paymentservice.domain.payment.dto.in;

import com.unionclass.paymentservice.domain.payment.vo.in.RequestPaymentReqVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestPaymentReqDto {

    private String memberUuid;
    private String orderId;
    private String orderName;
    private Long amount;
    private PaymentMethod paymentMethod;

    @Builder
    public RequestPaymentReqDto(String memberUuid, String orderId, String orderName, Long amount, PaymentMethod paymentMethod) {
        this.memberUuid = memberUuid;
        this.orderId = orderId;
        this.orderName = orderName;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    public static RequestPaymentReqDto of(String memberUuid, RequestPaymentReqVo requestPaymentReqVo) {
        return RequestPaymentReqDto.builder()
                .memberUuid(memberUuid)
                .orderId(requestPaymentReqVo.getOrderId())
                .orderName(requestPaymentReqVo.getOrderName())
                .amount(requestPaymentReqVo.getAmount())
                .paymentMethod(requestPaymentReqVo.getPaymentMethod())
                .build();
    }
}
