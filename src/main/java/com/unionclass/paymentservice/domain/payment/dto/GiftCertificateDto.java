package com.unionclass.paymentservice.domain.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GiftCertificateDto {

    private String approveNo;
    private String settlementStatus;

    @Builder
    public GiftCertificateDto(String approveNo, String settlementStatus) {
        this.approveNo = approveNo;
        this.settlementStatus = settlementStatus;
    }
}
