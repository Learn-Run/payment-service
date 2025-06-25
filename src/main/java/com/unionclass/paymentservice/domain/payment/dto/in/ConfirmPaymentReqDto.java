package com.unionclass.paymentservice.domain.payment.dto.in;

import com.unionclass.paymentservice.domain.payment.entity.Payment;
import com.unionclass.paymentservice.domain.payment.enums.Method;
import com.unionclass.paymentservice.domain.payment.enums.Status;
import com.unionclass.paymentservice.domain.payment.vo.in.ConfirmPaymentReqVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConfirmPaymentReqDto {

    private String memberUuid;
    private String paymentKey;
    private String orderId;
    private String orderName;
    private Method method;
    private Long amount;

    @Builder
    public ConfirmPaymentReqDto(
            String memberUuid, String paymentKey, String orderId,
            String orderName, Method method, Long amount
    ) {
        this.memberUuid = memberUuid;
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.orderName = orderName;
        this.method = method;
        this.amount = amount;
    }

    public static ConfirmPaymentReqDto of(String memberUuid, ConfirmPaymentReqVo confirmPaymentReqVo) {
        return ConfirmPaymentReqDto.builder()
                .memberUuid(memberUuid)
                .paymentKey(confirmPaymentReqVo.getPaymentKey())
                .orderId(confirmPaymentReqVo.getOrderId())
                .orderName(confirmPaymentReqVo.getOrderName())
                .method(confirmPaymentReqVo.getPaymentMethod())
                .amount(confirmPaymentReqVo.getAmount())
                .build();
    }

    public Payment toEntity(Long paymentUuid) {
        return Payment.builder()
                .uuid(paymentUuid)
                .memberUuid(memberUuid)
                .orderId(orderId)
                .orderName(orderName)
                .method(method)
                .totalAmount(amount)
                .status(Status.DONE)
                .paymentKey(paymentKey)
                .build();
    }
}
