package com.unionclass.paymentservice.domain.payment.dto.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetPaymentDetailsReqDto {

    private String memberUuid;
    private String paymentKey;

    @Builder
    public GetPaymentDetailsReqDto(String memberUuid, String paymentKey) {
        this.memberUuid = memberUuid;
        this.paymentKey = paymentKey;
    }

    public static GetPaymentDetailsReqDto of(String memberUuid, String paymentKey) {
        return GetPaymentDetailsReqDto.builder()
                .memberUuid(memberUuid)
                .paymentKey(paymentKey)
                .build();
    }
}
