package com.unionclass.paymentservice.domain.payment.vo.out;

import com.unionclass.paymentservice.domain.payment.enums.Method;
import com.unionclass.paymentservice.domain.payment.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
public class GetPaymentSummaryResVo {

    private String orderId;
    private String orderName;
    private String paymentKey;
    private Method paymentMethod;
    private Status paymentStatus;
    private Long totalAmount;
    private Long suppliedAmount;
    private Long vat;
    private String currency;
    private ZonedDateTime requestedAt;
    private ZonedDateTime approvedAt;

    @Builder
    public GetPaymentSummaryResVo(
            String orderId, String orderName, String paymentKey, Method paymentMethod, Status paymentStatus,
            Long totalAmount, Long suppliedAmount, Long vat, String currency, ZonedDateTime requestedAt,
            ZonedDateTime approvedAt
    ) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.paymentKey = paymentKey;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.totalAmount = totalAmount;
        this.suppliedAmount = suppliedAmount;
        this.vat = vat;
        this.currency = currency;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
    }
}
