package com.unionclass.paymentservice.domain.payment.application.port;

import com.unionclass.paymentservice.domain.payment.dto.in.CancelPaymentReqDto;
import com.unionclass.paymentservice.domain.payment.dto.in.ConfirmPaymentReqDto;
import com.unionclass.paymentservice.domain.payment.dto.in.RequestPaymentReqDto;
import com.unionclass.paymentservice.domain.payment.dto.out.GetPaymentDetailsResDto;

public interface PaymentGateway {

    /**
     * 결제 요청
     *
     * @param dto 결제 요청 정보
     * @return 결제창 URL
     */
    String requestPayment(RequestPaymentReqDto dto);

    /**
     * 결제 승인
     *
     * @param dto 결제 승인 정보
     * @return 결제 승인 결과
     */
    Object confirmPayment(ConfirmPaymentReqDto dto);

    /**
     * 결제 취소
     *
     * @param dto 결제 취소 정보
     * @return 결제 취소 결과
     */
    Object cancelPayment(CancelPaymentReqDto dto);

    /**
     * 결제 상세 정보 조회 (paymentKey)
     *
     * @param paymentKey 결제 키
     * @return 결제 상세 정보
     */
    GetPaymentDetailsResDto getPaymentDetailsByPaymentKey(String paymentKey);

    /**
     * 결제 상세 정보 조회 (orderId)
     *
     * @param orderId 주문 ID
     * @return 결제 상세 정보
     */
    GetPaymentDetailsResDto getPaymentDetailsByOrderId(String orderId);
} 