package com.unionclass.paymentservice.domain.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CashReceiptDto {

    private String type;
    private String receiptKey;
    private String issueNumber;
    private String receiptUrl;
    private Long amount;
    private Long taxFreeAmount;

    @Builder
    public CashReceiptDto(
            String type, String receiptKey, String issueNumber, String receiptUrl, Long amount, Long taxFreeAmount
    ) {
        this.type = type;
        this.receiptKey = receiptKey;
        this.issueNumber = issueNumber;
        this.receiptUrl = receiptUrl;
        this.amount = amount;
        this.taxFreeAmount = taxFreeAmount;
    }
}
