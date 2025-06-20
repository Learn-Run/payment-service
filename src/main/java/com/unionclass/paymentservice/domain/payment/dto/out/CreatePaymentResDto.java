package com.unionclass.paymentservice.domain.payment.dto.out;

import com.unionclass.paymentservice.domain.payment.vo.out.CreatePaymentResVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePaymentResDto {

    private String orderId;
    private String checkoutUrl;

    @Builder
    public CreatePaymentResDto(String orderId, String checkoutUrl) {
        this.orderId = orderId;
        this.checkoutUrl = checkoutUrl;
    }

    public static CreatePaymentResDto of(String orderId, String checkoutUrl) {
        return CreatePaymentResDto.builder()
                .orderId(orderId)
                .checkoutUrl(checkoutUrl)
                .build();
    }

    public CreatePaymentResVo toVo() {
        return CreatePaymentResVo.builder()
                .orderId(orderId)
                .checkoutUrl(checkoutUrl)
                .build();
    }
}
