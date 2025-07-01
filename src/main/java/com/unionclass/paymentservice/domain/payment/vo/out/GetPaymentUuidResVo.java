package com.unionclass.paymentservice.domain.payment.vo.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class GetPaymentUuidResVo {

    private LocalDateTime createdAt;
    private Long paymentUuid;

    @Builder
    public GetPaymentUuidResVo(LocalDateTime createdAt, Long paymentUuid) {
        this.createdAt = createdAt;
        this.paymentUuid = paymentUuid;
    }
}
