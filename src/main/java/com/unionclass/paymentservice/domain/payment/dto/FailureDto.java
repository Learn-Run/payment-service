package com.unionclass.paymentservice.domain.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FailureDto {

    private String code;
    private String message;

    @Builder
    public FailureDto(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
