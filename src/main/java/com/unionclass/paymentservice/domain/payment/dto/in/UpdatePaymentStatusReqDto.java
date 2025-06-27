package com.unionclass.paymentservice.domain.payment.dto.in;

import com.unionclass.paymentservice.domain.order.dto.in.UpdateOrderStatusReqDto;
import com.unionclass.paymentservice.domain.payment.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdatePaymentStatusReqDto {

    private String paymentKey;
    private Status status;

    @Builder
    public UpdatePaymentStatusReqDto(String paymentKey, Status status) {
        this.paymentKey = paymentKey;
        this.status = status;
    }
}
