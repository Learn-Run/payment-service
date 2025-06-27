package com.unionclass.paymentservice.domain.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetCancelsDto {

    private String orderId;
    private Long cancelAmount;
    private String cancelStatus;
    private String cancelReason;
    private ZonedDateTime canceledAt;

    @Builder
    public GetCancelsDto(
            String orderId, Long cancelAmount, String cancelStatus, String cancelReason, ZonedDateTime canceledAt
    ) {
        this.orderId = orderId;
        this.cancelAmount = cancelAmount;
        this.cancelStatus = cancelStatus;
        this.cancelReason = cancelReason;
        this.canceledAt = canceledAt;
    }
}
