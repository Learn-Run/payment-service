package com.unionclass.paymentservice.domain.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CashReceiptsDto {

    private String receiptKey;
    private String orderId;
    private String orderName;
    private String type;
    private String issueNumber;
    private String receiptUrl;
    private String businessNumber;
    private String transactionType;
    private Long amount;
    private Long taxFreeAmount;
    private String issueStatus;
    private Object failure;
    private String customerIdentityNumber;
    private ZonedDateTime requestedAt;

    @Builder
    public CashReceiptsDto(
            String receiptKey, String orderId, String orderName, String type, String issueNumber,
            String receiptUrl, String businessNumber, String transactionType, Long amount, Long taxFreeAmount,
            String issueStatus, Object failure, String customerIdentityNumber, ZonedDateTime requestedAt
    ) {
        this.receiptKey = receiptKey;
        this.orderId = orderId;
        this.orderName = orderName;
        this.type = type;
        this.issueNumber = issueNumber;
        this.receiptUrl = receiptUrl;
        this.businessNumber = businessNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.taxFreeAmount = taxFreeAmount;
        this.issueStatus = issueStatus;
        this.failure = failure;
        this.customerIdentityNumber = customerIdentityNumber;
        this.requestedAt = requestedAt;
    }
}
