package com.unionclass.paymentservice.domain.payment.dto.in;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetTossPaymentFailureReqDto {

    private String code;
    private String message;
}
