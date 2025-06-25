package com.unionclass.paymentservice.domain.payment.dto.out;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unionclass.paymentservice.domain.payment.vo.out.GetPaymentDetailsResVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetPaymentDetailsResDto {

    private String mId;
    private String lastTransactionKey;
    private String paymentKey;
    private String orderId;
    private String orderName;
    private int taxExemptionAmount;
    private String status;
    private String requestedAt;
    private String approvedAt;
    private String method;
    private String currency;
    private int totalAmount;
    private int balanceAmount;
    private int suppliedAmount;
    private int vat;
    private boolean useEscrow;
    private boolean cultureExpense;
    private boolean isPartialCancelable;
    private String type;
    private String country;

    private GetPaymentDetailsResVo.Card card;
    private GetPaymentDetailsResVo.EasyPay easyPay;
    private GetPaymentDetailsResVo.Receipt receipt;
    private GetPaymentDetailsResVo.Checkout checkout;

    public GetPaymentDetailsResVo toVo() {
        return GetPaymentDetailsResVo.builder()
                .mId(mId)
                .lastTransactionKey(lastTransactionKey)
                .paymentKey(paymentKey)
                .orderId(orderId)
                .orderName(orderName)
                .taxExemptionAmount(taxExemptionAmount)
                .status(status)
                .requestedAt(requestedAt)
                .approvedAt(approvedAt)
                .method(method)
                .currency(currency)
                .totalAmount(totalAmount)
                .balanceAmount(balanceAmount)
                .suppliedAmount(suppliedAmount)
                .vat(vat)
                .useEscrow(useEscrow)
                .cultureExpense(cultureExpense)
                .isPartialCancelable(isPartialCancelable)
                .type(type)
                .country(country)
                .card(card)
                .easyPay(easyPay)
                .receipt(receipt)
                .checkout(checkout)
                .build();
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Card {

        private String issuerCode;
        private String acquirerCode;
        private String number;
        private int installmentPlanMonths;
        private boolean isInterestFree;
        private String interestPayer;
        private String approveNo;
        private boolean useCardPoint;
        private String cardType;
        private String ownerType;
        private String acquireStatus;
        private int amount;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class EasyPay {

        private String provider;
        private int amount;
        private int discountAmount;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Receipt {

        private String url;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Checkout {

        private String url;
    }
}
