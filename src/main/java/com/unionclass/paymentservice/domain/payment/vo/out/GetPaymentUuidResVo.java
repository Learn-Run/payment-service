package com.unionclass.paymentservice.domain.payment.vo.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetPaymentUuidResVo {

    private Long paymentUuid;

    @Builder
    public GetPaymentUuidResVo(Long paymentUuid) {
        this.paymentUuid = paymentUuid;
    }
}
