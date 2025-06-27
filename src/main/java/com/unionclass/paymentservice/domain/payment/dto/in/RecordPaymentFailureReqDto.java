package com.unionclass.paymentservice.domain.payment.dto.in;

import com.unionclass.paymentservice.domain.payment.dto.FailureDto;
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
    private String failCode;
    private String failReason;

    @Builder
    public RecordPaymentFailureReqDto(
            String memberUuid, String paymentKey, String orderId, String failCode, String failReason
    ) {
        this.memberUuid = memberUuid;
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.failCode = failCode;
        this.failReason = failReason;
    }

    public static RecordPaymentFailureReqDto of(
            ConfirmPaymentReqDto confirmPaymentReqDto, FailureDto failureDto
    ) {
        return RecordPaymentFailureReqDto.builder()
                .memberUuid(confirmPaymentReqDto.getMemberUuid())
                .paymentKey(confirmPaymentReqDto.getPaymentKey())
                .orderId(confirmPaymentReqDto.getOrderId())
                .failCode(failureDto.getCode())
                .failReason(failureDto.getMessage())
                .build();
    }

    public PaymentFailure toEntity(Long uuid) {

        return PaymentFailure.builder()
                .uuid(uuid)
                .memberUuid(memberUuid)
                .paymentKey(paymentKey)
                .orderId(orderId)
                .failCode(failCode)
                .failReason(failReason)
                .build();
    }
}
