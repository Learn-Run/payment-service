package com.unionclass.paymentservice.domain.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MobilePhoneDto {

    private String customerMobilePhone;
    private String settlementStatus;
    private String receiptUrl;

    @Builder
    public MobilePhoneDto(String customerMobilePhone, String settlementStatus, String receiptUrl) {
        this.customerMobilePhone = customerMobilePhone;
        this.settlementStatus = settlementStatus;
        this.receiptUrl = receiptUrl;
    }
}
