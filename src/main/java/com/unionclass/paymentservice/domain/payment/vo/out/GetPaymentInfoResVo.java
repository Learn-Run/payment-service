package com.unionclass.paymentservice.domain.payment.vo.out;

import com.unionclass.paymentservice.domain.payment.enums.Method;
import com.unionclass.paymentservice.domain.payment.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
public class GetPaymentInfoResVo {

    private String orderId;
    private String orderName;
    private String paymentKey;
    private Method paymentMethod;
    private Status paymentStatus;
    private Long totalAmount;
    private ZonedDateTime requestedAt;
    private ZonedDateTime approvedAt;

    @Builder
    public GetPaymentInfoResVo(
            String orderId, String orderName, String paymentKey, Method paymentMethod,
            Status paymentStatus, Long totalAmount, ZonedDateTime requestedAt, ZonedDateTime approvedAt
    ) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.paymentKey = paymentKey;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.totalAmount = totalAmount;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
    }
}
