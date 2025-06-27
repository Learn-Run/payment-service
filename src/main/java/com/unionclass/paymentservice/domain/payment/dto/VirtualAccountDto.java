package com.unionclass.paymentservice.domain.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VirtualAccountDto {

    private String accountType;
    private String accountNumber;
    private String bankCode;
    private String customerName;
    private String dueDate;
    private String refundStatus;
    private boolean expired;
    private String settlementStatus;
    private Object refundReceiveAccount;

    @Builder
    public VirtualAccountDto(
            String accountType, String accountNumber, String bankCode, String customerName, String dueDate,
            String refundStatus, boolean expired, String settlementStatus, Object refundReceiveAccount
    ) {
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.bankCode = bankCode;
        this.customerName = customerName;
        this.dueDate = dueDate;
        this.refundStatus = refundStatus;
        this.expired = expired;
        this.settlementStatus = settlementStatus;
        this.refundReceiveAccount = refundReceiveAccount;
    }
}
