package com.unionclass.paymentservice.domain.payment.application;

import com.unionclass.paymentservice.domain.order.application.OrderService;
import com.unionclass.paymentservice.domain.order.dto.in.UpdateOrderStatusReqDto;
import com.unionclass.paymentservice.domain.payment.dto.GetCancelsDto;
import com.unionclass.paymentservice.domain.payment.dto.in.CancelPaymentReqDto;
import com.unionclass.paymentservice.domain.payment.dto.in.CreatePaymentCancelReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentCancelFacadeImpl implements PaymentCancelFacade {

    private final PaymentCancelService paymentCancelService;
    private final OrderService orderService;

    @Override
    public void cancelPayment(CancelPaymentReqDto dto, GetCancelsDto getCancelsDto) {

        paymentCancelService.createPaymentCancel(CreatePaymentCancelReqDto.of(dto, getCancelsDto));

        orderService.updateOrderStatus(UpdateOrderStatusReqDto.from(getCancelsDto.getOrderId()));

        // 포인트 충전

    }
}
