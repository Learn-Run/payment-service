package com.unionclass.paymentservice.domain.payment.presentation;

import com.unionclass.paymentservice.common.response.BaseResponseEntity;
import com.unionclass.paymentservice.common.response.ResponseMessage;
import com.unionclass.paymentservice.domain.payment.application.PaymentService;
import com.unionclass.paymentservice.domain.payment.dto.in.ConfirmPaymentReqDto;
import com.unionclass.paymentservice.domain.payment.dto.in.CreatePaymentReqDto;
import com.unionclass.paymentservice.domain.payment.vo.in.ConfirmPaymentReqVo;
import com.unionclass.paymentservice.domain.payment.vo.in.CreatePaymentReqVo;
import com.unionclass.paymentservice.domain.payment.vo.out.CreatePaymentResVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
@Tag(name = "payment")
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * /api/v1/payment
     *
     * 1. 결제 생성
     * 2. 결제 승인
     * 3. 결제 취소 (환불)
     */

    /**
     * 1. 결제 생성
     *
     * @param memberUuid
     * @param createPaymentReqVo
     * @return
     */
    @PostMapping
    public BaseResponseEntity<CreatePaymentResVo> createPayment(
            @RequestHeader("X-Member-UUID") String memberUuid,
            @RequestBody CreatePaymentReqVo createPaymentReqVo
    ) {
        return new BaseResponseEntity<>(
                ResponseMessage.SUCCESS_CREATE_PAYMENT.getMessage(),
                paymentService.createPayment(CreatePaymentReqDto.of(memberUuid, createPaymentReqVo)).toVo());
    }

    /**
     * 2. 결제 승인
     *
     * @param memberUuid
     * @param confirmPaymentReqVo
     * @return
     */
    @PostMapping("/confirm")
    public BaseResponseEntity<CreatePaymentResVo> confirmPayment(
            @RequestHeader("X-Member-UUID") String memberUuid,
            @RequestBody ConfirmPaymentReqVo confirmPaymentReqVo
    ) {
        paymentService.confirmPayment(ConfirmPaymentReqDto.of(memberUuid, confirmPaymentReqVo));
        return new BaseResponseEntity<>(ResponseMessage.SUCCESS_CONFIRM_PAYMENT.getMessage());
    }
}
