package com.unionclass.paymentservice.domain.payment.dto.out;

import com.unionclass.paymentservice.domain.payment.dto.GetFailureDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConfirmPaymentResDto {

    private int code;
    private String message;

    @Builder
    public ConfirmPaymentResDto(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ConfirmPaymentResDto of(int code, String message) {

        return ConfirmPaymentResDto.builder()
                .code(code)
                .message(message)
                .build();
    }

    public static ConfirmPaymentResDto of(GetFailureDto dto) {

        return ConfirmPaymentResDto.builder()
                .code(Integer.parseInt(dto.getCode()))
                .message(dto.getMessage())
                .build();
    }
}
