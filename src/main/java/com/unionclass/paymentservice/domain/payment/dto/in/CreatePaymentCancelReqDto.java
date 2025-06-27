package com.unionclass.paymentservice.domain.payment.dto.in;

import com.unionclass.paymentservice.domain.payment.dto.GetCancelsDto;
import com.unionclass.paymentservice.domain.payment.entity.PaymentCancel;
import com.unionclass.paymentservice.domain.payment.enums.CancelStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
public class CreatePaymentCancelReqDto {

    private String paymentKey;
    private String memberUuid;
    private String orderId;
    private Long amount;
    private String cancelReason;
    private CancelStatus cancelStatus;
    private ZonedDateTime canceledAt;

    @Builder
    public CreatePaymentCancelReqDto(
            String paymentKey, String memberUuid, String orderId, Long amount,
            String cancelReason, CancelStatus cancelStatus, ZonedDateTime canceledAt
    ) {
        this.paymentKey = paymentKey;
        this.memberUuid = memberUuid;
        this.orderId = orderId;
        this.amount = amount;
        this.cancelReason = cancelReason;
        this.cancelStatus = cancelStatus;
        this.canceledAt = canceledAt;
    }

    public static CreatePaymentCancelReqDto of(
            CancelPaymentReqDto cancelPaymentReqDto, GetCancelsDto getCancelsDto
    ) {
        return CreatePaymentCancelReqDto.builder()
                .paymentKey(cancelPaymentReqDto.getPaymentKey())
                .memberUuid(cancelPaymentReqDto.getMemberUuid())
                .orderId(getCancelsDto.getOrderId())
                .amount(getCancelsDto.getCancelAmount())
                .cancelReason(getCancelsDto.getCancelReason())
                .cancelStatus(CancelStatus.fromString(getCancelsDto.getCancelStatus()))
                .canceledAt(getCancelsDto.getCanceledAt())
                .build();
    }

    public PaymentCancel toEntity(Long uuid) {

        return PaymentCancel.builder()
                .uuid(uuid)
                .paymentKey(paymentKey)
                .memberUuid(memberUuid)
                .orderId(orderId)
                .amount(amount)
                .cancelReason(cancelReason)
                .cancelStatus(cancelStatus)
                .canceledAt(canceledAt)
                .build();
    }
}
