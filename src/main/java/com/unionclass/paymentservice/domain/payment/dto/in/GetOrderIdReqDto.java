package com.unionclass.paymentservice.domain.payment.dto.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetOrderIdReqDto {

    private String memberUuid;
    private String orderId;

    @Builder
    public GetOrderIdReqDto(String memberUuid, String orderId) {
        this.memberUuid = memberUuid;
        this.orderId = orderId;
    }

    public static GetOrderIdReqDto of(String memberUuid, String orderId) {

        return GetOrderIdReqDto.builder()
                .memberUuid(memberUuid)
                .orderId(orderId)
                .build();
    }
}
