package com.unionclass.paymentservice.domain.payment.dto.in;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unionclass.paymentservice.domain.payment.entity.Payment;
import com.unionclass.paymentservice.domain.payment.enums.Method;
import com.unionclass.paymentservice.domain.payment.enums.Status;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreatePaymentReqDto {

    private String orderId;
    private String orderName;
    private String paymentKey;
    private String method;
    private String status;
    private Long totalAmount;
    private Long suppliedAmount;
    private Long vat;
    private Long taxFreeAmount;
    private String country;
    private String currency;
    private ZonedDateTime requestedAt;
    private ZonedDateTime approvedAt;
    private boolean isPartialCancelable;

    public Payment toEntity(Long uuid, String memberUuid) {
        return Payment.builder()
                .uuid(uuid)
                .memberUuid(memberUuid)
                .orderId(orderId)
                .orderName(orderName)
                .paymentKey(paymentKey)
                .method(Method.fromString(method))
                .status(Status.fromString(status))
                .totalAmount(totalAmount)
                .suppliedAmount(suppliedAmount)
                .vat(vat)
                .taxFreeAmount(taxFreeAmount)
                .country(country)
                .currency(currency)
                .requestedAt(requestedAt)
                .approvedAt(approvedAt)
                .isPartialCancelable(isPartialCancelable)
                .build();
    }
}
