package com.unionclass.paymentservice.domain.payment.dto.in;

import com.unionclass.paymentservice.domain.payment.entity.Payment;
import com.unionclass.paymentservice.domain.payment.enums.Method;
import com.unionclass.paymentservice.domain.payment.enums.Status;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreatePaymentReqDto {

    private String orderId;
    private String orderName;
    private String paymentKey;
    private Method method;
    private Status status;
    private Long totalAmount;
    private Long suppliedAmount;
    private Long vat;
    private Long taxFreeAmount;
    private String country;
    private String currency;
    private LocalDateTime requestedAt;
    private String checkout;
    private boolean isPartialCancelable;

    public Payment toEntity(Long uuid, String memberUuid) {
        return Payment.builder()
                .uuid(uuid)
                .memberUuid(memberUuid)
                .orderId(orderId)
                .orderName(orderName)
                .paymentKey(paymentKey)
                .method(method)
                .status(status)
                .totalAmount(totalAmount)
                .suppliedAmount(suppliedAmount)
                .vat(vat)
                .taxFreeAmount(taxFreeAmount)
                .country(country)
                .currency(currency)
                .requestedAt(requestedAt)
                .checkout(checkout)
                .isPartialCancelable(isPartialCancelable)
                .build();
    }
}
