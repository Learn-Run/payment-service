package com.unionclass.paymentservice.domain.payment.dto.in;

import com.unionclass.paymentservice.domain.payment.enums.Method;
import com.unionclass.paymentservice.domain.payment.enums.Status;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreatePaymentReqDto {

    private Long uuid;
    private String memberUuid;
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

}
