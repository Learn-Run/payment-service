package com.unionclass.paymentservice.domain.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EasyPayDto {

    private String provider;
    private Long amount;
    private Long discountAmount;

    @Builder
    public EasyPayDto(String provider, Long amount, Long discountAmount) {
        this.provider = provider;
        this.amount = amount;
        this.discountAmount = discountAmount;
    }
}
