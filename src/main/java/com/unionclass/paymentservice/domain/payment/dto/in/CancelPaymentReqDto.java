package com.unionclass.paymentservice.domain.payment.dto.in;

import com.unionclass.paymentservice.domain.payment.entity.Payment;
import com.unionclass.paymentservice.domain.payment.entity.RefundHistory;
import com.unionclass.paymentservice.domain.payment.enums.RefundProcessStatus;
import com.unionclass.paymentservice.domain.payment.vo.in.CancelPaymentReqVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CancelPaymentReqDto {

    private String memberUuid;
    private String paymentKey;
    private String cancelReason;

    @Builder
    public CancelPaymentReqDto(String memberUuid, String paymentKey, String cancelReason) {
        this.memberUuid = memberUuid;
        this.paymentKey = paymentKey;
        this.cancelReason = cancelReason;
    }

    public static CancelPaymentReqDto of(String memberUuid, String paymentKey, CancelPaymentReqVo cancelPaymentReqVo) {
        return CancelPaymentReqDto.builder()
                .memberUuid(memberUuid)
                .paymentKey(paymentKey)
                .cancelReason(cancelPaymentReqVo.getCancelReason())
                .build();
    }

    public RefundHistory toEntity(Long refundHistoryUuid, Payment payment) {
        return RefundHistory.builder()
                .uuid(refundHistoryUuid)
                .paymentUuid(payment.getUuid())
                .memberUuid(memberUuid)
                .orderId(payment.getOrderId())
                .paymentKey(paymentKey)
                .amount(payment.getAmount())
                .cancelReason(cancelReason)
                .refundProcessStatus(RefundProcessStatus.COMPLETED)
                .approvedAt(LocalDateTime.now())
                .build();
    }
}
