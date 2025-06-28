package com.unionclass.paymentservice.domain.payment.dto.out;

import com.unionclass.paymentservice.domain.payment.entity.Payment;
import com.unionclass.paymentservice.domain.payment.enums.Method;
import com.unionclass.paymentservice.domain.payment.enums.Status;
import com.unionclass.paymentservice.domain.payment.vo.out.GetPaymentSummaryResVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
public class GetPaymentSummaryResDto {

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
    public GetPaymentSummaryResDto(
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

    public static GetPaymentSummaryResDto from(Payment payment) {

        return GetPaymentSummaryResDto.builder()
                .orderId(payment.getOrderId())
                .orderName(payment.getOrderName())
                .paymentKey(payment.getPaymentKey())
                .paymentMethod(payment.getMethod())
                .paymentStatus(payment.getStatus())
                .totalAmount(payment.getTotalAmount())
                .suppliedAmount(payment.getSuppliedAmount())
                .vat(payment.getVat())
                .currency(payment.getCurrency())
                .requestedAt(payment.getRequestedAt())
                .approvedAt(payment.getApprovedAt())
                .build();
    }

    public GetPaymentSummaryResVo toVo() {

        return GetPaymentSummaryResVo.builder()
                .orderId(orderId)
                .orderName(orderName)
                .paymentKey(paymentKey)
                .paymentMethod(paymentMethod)
                .paymentStatus(paymentStatus)
                .totalAmount(totalAmount)
                .suppliedAmount(suppliedAmount)
                .vat(vat)
                .currency(currency)
                .requestedAt(requestedAt)
                .approvedAt(approvedAt)
                .build();
    }
}
