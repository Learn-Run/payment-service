package com.unionclass.paymentservice.domain.payment.dto.out;

import com.unionclass.paymentservice.domain.payment.enums.Method;
import com.unionclass.paymentservice.domain.payment.enums.Status;
import com.unionclass.paymentservice.domain.payment.vo.out.GetPaymentInfoResVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
public class GetPaymentInfoResDto {

    private String orderId;
    private String orderName;
    private String paymentKey;
    private Method paymentMethod;
    private Status paymentStatus;
    private Long totalAmount;
    private ZonedDateTime requestedAt;
    private ZonedDateTime approvedAt;
    private LocalDateTime createdAt;
    private Long paymentUuid;

    @Builder
    public GetPaymentInfoResDto(
            String orderId, String orderName, String paymentKey, Method paymentMethod, Status paymentStatus,
            Long totalAmount, ZonedDateTime requestedAt, ZonedDateTime approvedAt, LocalDateTime createdAt, Long paymentUuid
    ) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.paymentKey = paymentKey;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.totalAmount = totalAmount;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
        this.createdAt = createdAt;
        this.paymentUuid = paymentUuid;
    }

    public GetPaymentInfoResVo toVo() {

        return GetPaymentInfoResVo.builder()
                .orderId(orderId)
                .orderName(orderName)
                .paymentKey(paymentKey)
                .paymentMethod(paymentMethod)
                .paymentStatus(paymentStatus)
                .totalAmount(totalAmount)
                .requestedAt(requestedAt)
                .approvedAt(approvedAt)
                .build();
    }
}
