package com.unionclass.paymentservice.domain.payment.dto.in;

import com.unionclass.paymentservice.domain.order.dto.out.CreateOrderResDto;
import com.unionclass.paymentservice.domain.payment.enums.Method;
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
    private Method method;

    @Builder
    public RequestPaymentReqDto(String memberUuid, String orderId, String orderName, Long amount, Method method) {
        this.memberUuid = memberUuid;
        this.orderId = orderId;
        this.orderName = orderName;
        this.amount = amount;
        this.method = method;
    }

    public static RequestPaymentReqDto of(String memberUuid, RequestPaymentReqVo requestPaymentReqVo) {
        return RequestPaymentReqDto.builder()
                .memberUuid(memberUuid)
                .orderId(requestPaymentReqVo.getOrderId())
                .orderName(requestPaymentReqVo.getOrderName())
                .amount(requestPaymentReqVo.getAmount())
                .method(requestPaymentReqVo.getPaymentMethod())
                .build();
    }

    public static RequestPaymentReqDto of(CreateOrderResDto order, CreateOrderAndRequestPaymentReqDto payment) {

        return RequestPaymentReqDto.builder()
                .memberUuid(payment.getMemberUuid())
                .orderId(order.getOrderId())
                .orderName(payment.getOrderName())
                .amount(payment.getPaymentAmount())
                .method(payment.getPaymentMethod())
                .build();
    }
}
