package com.unionclass.paymentservice.domain.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardDto {

    private String issuerCode;
    private String acquirerCode;
    private String number;
    private Integer installmentPlanMonths;
    private Boolean isInterestFree;
    private String interestPayer;
    private String approveNo;
    private boolean useCardPoint;
    private String cardType;
    private String ownerType;
    private String acquireStatus;
    private Long amount;

    @Builder
    public CardDto(
            String issuerCode, String acquirerCode, String number, Integer installmentPlanMonths,
            Boolean isInterestFree, String interestPayer, String approveNo, boolean useCardPoint,
            String cardType, String ownerType, String acquireStatus, Long amount
    ) {
        this.issuerCode = issuerCode;
        this.acquirerCode = acquirerCode;
        this.number = number;
        this.installmentPlanMonths = installmentPlanMonths;
        this.isInterestFree = isInterestFree;
        this.interestPayer = interestPayer;
        this.approveNo = approveNo;
        this.useCardPoint = useCardPoint;
        this.cardType = cardType;
        this.ownerType = ownerType;
        this.acquireStatus = acquireStatus;
        this.amount = amount;
    }
}
