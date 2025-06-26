package com.unionclass.paymentservice.domain.payment.dto.in;

import com.unionclass.paymentservice.domain.payment.entity.PaymentFailure;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecordPaymentFailureReqDto {

    private String memberUuid;
    private String paymentKey;
    private String orderId;
    private String orderName;
    private String failCode;
    private String failReason;

    @Builder
    public RecordPaymentFailureReqDto(
            String memberUuid, String paymentKey, String orderId, String orderName, String failCode, String failReason
    ) {
        this.memberUuid = memberUuid;
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.orderName = orderName;
        this.failCode = failCode;
        this.failReason = failReason;
    }

    public static RecordPaymentFailureReqDto of(
            ConfirmPaymentReqDto confirmPaymentReqDto, GetTossPaymentFailureReqDto getTossPaymentFailureReqDto
    ) {
        return RecordPaymentFailureReqDto.builder()
                .memberUuid(confirmPaymentReqDto.getMemberUuid())
                .paymentKey(confirmPaymentReqDto.getPaymentKey())
                .orderId(confirmPaymentReqDto.getOrderId())
                .orderName(confirmPaymentReqDto.getOrderName())
                .failCode(getTossPaymentFailureReqDto.getCode())
                .failReason(getTossPaymentFailureReqDto.getMessage())
                .build();
    }

    public PaymentFailure toEntity(Long uuid) {

        return PaymentFailure.builder()
                .uuid(uuid)
                .memberUuid(memberUuid)
                .paymentKey(paymentKey)
                .orderId(orderId)
                .orderName(orderName)
                .failCode(failCode)
                .failReason(failReason)
                .build();
    }
}
