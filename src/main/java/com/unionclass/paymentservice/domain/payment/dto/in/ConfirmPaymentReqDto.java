package com.unionclass.paymentservice.domain.payment.dto.in;

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
    private Long amount;

    @Builder
    public ConfirmPaymentReqDto(
            String memberUuid, String paymentKey, String orderId, Long amount
    ) {
        this.memberUuid = memberUuid;
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.amount = amount;
    }

    public static ConfirmPaymentReqDto of(String memberUuid, ConfirmPaymentReqVo confirmPaymentReqVo) {
        return ConfirmPaymentReqDto.builder()
                .memberUuid(memberUuid)
                .paymentKey(confirmPaymentReqVo.getPaymentKey())
                .orderId(confirmPaymentReqVo.getOrderId())
                .amount(confirmPaymentReqVo.getAmount())
                .build();
    }
}
