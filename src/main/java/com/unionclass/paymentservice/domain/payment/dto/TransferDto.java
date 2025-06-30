package com.unionclass.paymentservice.domain.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferDto {

    private String bankCode;
    private String settlementStatus;

    @Builder
    public TransferDto(String bankCode, String settlementStatus) {
        this.bankCode = bankCode;
        this.settlementStatus = settlementStatus;
    }
}
