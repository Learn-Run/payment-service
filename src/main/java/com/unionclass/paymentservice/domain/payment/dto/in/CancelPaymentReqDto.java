package com.unionclass.paymentservice.domain.payment.dto.in;

import com.unionclass.paymentservice.domain.payment.vo.in.CancelPaymentReqVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
