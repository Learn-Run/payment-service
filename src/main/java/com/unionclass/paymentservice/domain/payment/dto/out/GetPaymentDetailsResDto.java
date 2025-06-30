package com.unionclass.paymentservice.domain.payment.dto.out;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unionclass.paymentservice.domain.payment.dto.*;
import com.unionclass.paymentservice.domain.payment.vo.out.GetPaymentDetailsResVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetPaymentDetailsResDto {

    private String mId;
    private String lastTransactionKey;
    private String paymentKey;
    private String orderId;
    private String orderName;
    private Integer taxExemptionAmount;
    private String status;
    private ZonedDateTime requestedAt;
    private ZonedDateTime approvedAt;
    private boolean useEscrow;
    private boolean cultureExpense;
    private CardDto card;
    private VirtualAccountDto virtualAccount;
    private TransferDto transfer;
    private MobilePhoneDto mobilePhone;
    private GiftCertificateDto giftCertificate;
    private CashReceiptDto cashReceipt;
    private CashReceiptsDto cashReceipts;
    private DiscountDto discount;
    private CancelsDto cancels;
    private String secret;
    private String type;
    private EasyPayDto easyPay;
    private String country;
    private FailureDto failure;
    private boolean isPartialCancelable;
    private ReceiptDto receipt;
    private CheckoutDto checkout;
    private String currency;
    private Long totalAmount;
    private Long balanceAmount;
    private Long suppliedAmount;
    private Long vat;
    private Long taxFreeAmount;
    private Object metadata;
    private String version;

    @Builder
    public GetPaymentDetailsResDto(
            String mId, String lastTransactionKey, String paymentKey, String orderId, String orderName,
            Integer taxExemptionAmount, String status, ZonedDateTime requestedAt, ZonedDateTime approvedAt,
            boolean useEscrow, boolean cultureExpense, CardDto card, VirtualAccountDto virtualAccount,
            TransferDto transfer, MobilePhoneDto mobilePhone, GiftCertificateDto giftCertificate,
            CashReceiptDto cashReceipt, CashReceiptsDto cashReceipts, DiscountDto discount, CancelsDto cancels,
            String secret, String type, EasyPayDto easyPay, String country, FailureDto failure,
            boolean isPartialCancelable, ReceiptDto receipt, CheckoutDto checkout, String currency, Long totalAmount,
            Long balanceAmount, Long suppliedAmount, Long vat, Long taxFreeAmount, Object metadata, String version
    ) {
        this.mId = mId;
        this.lastTransactionKey = lastTransactionKey;
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.orderName = orderName;
        this.taxExemptionAmount = taxExemptionAmount;
        this.status = status;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
        this.useEscrow = useEscrow;
        this.cultureExpense = cultureExpense;
        this.card = card;
        this.virtualAccount = virtualAccount;
        this.transfer = transfer;
        this.mobilePhone = mobilePhone;
        this.giftCertificate = giftCertificate;
        this.cashReceipt = cashReceipt;
        this.cashReceipts = cashReceipts;
        this.discount = discount;
        this.cancels = cancels;
        this.secret = secret;
        this.type = type;
        this.easyPay = easyPay;
        this.country = country;
        this.failure = failure;
        this.isPartialCancelable = isPartialCancelable;
        this.receipt = receipt;
        this.checkout = checkout;
        this.currency = currency;
        this.totalAmount = totalAmount;
        this.balanceAmount = balanceAmount;
        this.suppliedAmount = suppliedAmount;
        this.vat = vat;
        this.taxFreeAmount = taxFreeAmount;
        this.metadata = metadata;
        this.version = version;
    }

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
                .useEscrow(useEscrow)
                .cultureExpense(cultureExpense)
                .card(card)
                .virtualAccount(virtualAccount)
                .transfer(transfer)
                .mobilePhone(mobilePhone)
                .giftCertificate(giftCertificate)
                .cashReceipt(cashReceipt)
                .cashReceipts(cashReceipts)
                .discount(discount)
                .cancels(cancels)
                .secret(secret)
                .type(type)
                .easyPay(easyPay)
                .country(country)
                .failure(failure)
                .isPartialCancelable(isPartialCancelable)
                .receipt(receipt)
                .checkout(checkout)
                .currency(currency)
                .totalAmount(totalAmount)
                .balanceAmount(balanceAmount)
                .suppliedAmount(suppliedAmount)
                .vat(vat)
                .taxFreeAmount(taxFreeAmount)
                .metadata(metadata)
                .version(version)
                .build();
    }
}
