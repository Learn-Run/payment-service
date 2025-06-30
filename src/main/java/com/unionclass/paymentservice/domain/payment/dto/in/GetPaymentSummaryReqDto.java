package com.unionclass.paymentservice.domain.payment.dto.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetPaymentSummaryReqDto {

    private String memberUuid;
    private Long paymentUuid;

    @Builder
    public GetPaymentSummaryReqDto(String memberUuid, Long paymentUuid) {
        this.memberUuid = memberUuid;
        this.paymentUuid = paymentUuid;
    }

    public static GetPaymentSummaryReqDto of(String memberUuid, Long paymentUuid) {

        return GetPaymentSummaryReqDto.builder()
                .memberUuid(memberUuid)
                .paymentUuid(paymentUuid)
                .build();
    }
}
