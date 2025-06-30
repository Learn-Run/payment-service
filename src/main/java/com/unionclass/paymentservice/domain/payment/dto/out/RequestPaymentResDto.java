package com.unionclass.paymentservice.domain.payment.dto.out;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unionclass.paymentservice.domain.payment.vo.out.RequestPaymentResVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestPaymentResDto {

    private String url;

    @Builder
    public RequestPaymentResDto(String url) {
        this.url = url;
    }

    public static RequestPaymentResDto from(String url) {
        return RequestPaymentResDto.builder()
                .url(url)
                .build();
    }

    public RequestPaymentResVo toVo() {
        return RequestPaymentResVo.builder()
                .checkoutUrl(url)
                .build();
    }
}
