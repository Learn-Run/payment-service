package com.unionclass.paymentservice.domain.payment.dto.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetPaymentKeyReqDto {

    private String memberUuid;
    private String paymentKey;

    @Builder
    public GetPaymentKeyReqDto(String memberUuid, String paymentKey) {
        this.memberUuid = memberUuid;
        this.paymentKey = paymentKey;
    }

    public static GetPaymentKeyReqDto of(String memberUuid, String paymentKey) {
        return GetPaymentKeyReqDto.builder()
                .memberUuid(memberUuid)
                .paymentKey(paymentKey)
                .build();
    }
}
