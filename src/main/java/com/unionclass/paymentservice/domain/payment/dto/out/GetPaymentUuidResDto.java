package com.unionclass.paymentservice.domain.payment.dto.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
public class GetPaymentUuidResDto {

    private LocalDateTime createdAt;
    private Long paymentUuid;

    @Builder
    public GetPaymentUuidResDto(LocalDateTime createdAt, Long paymentUuid) {
        this.createdAt = createdAt;
        this.paymentUuid = paymentUuid;
    }
}
