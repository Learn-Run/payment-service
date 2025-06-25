package com.unionclass.paymentservice.domain.payment.vo.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetPaymentDetailsResVo {

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

    private Card card;
    private EasyPay easyPay;
    private Receipt receipt;
    private Checkout checkout;

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
