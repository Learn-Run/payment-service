package com.unionclass.paymentservice.domain.payment.vo.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestPaymentResVo {

    private String checkoutUrl;

    @Builder
    public RequestPaymentResVo(String checkoutUrl) {
        this.checkoutUrl = checkoutUrl;
    }
}
