package com.unionclass.paymentservice.domain.payment.dto.out;

import com.unionclass.paymentservice.domain.payment.vo.out.RequestPaymentResVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestPaymentResDto {

    private String orderId;
    private String checkoutUrl;

    @Builder
    public RequestPaymentResDto(String orderId, String checkoutUrl) {
        this.orderId = orderId;
        this.checkoutUrl = checkoutUrl;
    }

    public static RequestPaymentResDto of(String orderId, String checkoutUrl) {
        return RequestPaymentResDto.builder()
                .orderId(orderId)
                .checkoutUrl(checkoutUrl)
                .build();
    }

    public RequestPaymentResVo toVo() {
        return RequestPaymentResVo.builder()
                .orderId(orderId)
                .checkoutUrl(checkoutUrl)
                .build();
    }
}
