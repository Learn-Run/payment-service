package com.unionclass.paymentservice.domain.payment.application;

import com.unionclass.paymentservice.common.exception.BaseException;
import com.unionclass.paymentservice.common.exception.ErrorCode;
import com.unionclass.paymentservice.domain.order.application.OrderService;
import com.unionclass.paymentservice.domain.order.dto.in.CreateOrderReqDto;
import com.unionclass.paymentservice.domain.order.dto.out.CreateOrderResDto;
import com.unionclass.paymentservice.domain.payment.dto.in.CreateOrderAndRequestPaymentReqDto;
import com.unionclass.paymentservice.domain.payment.dto.in.RequestPaymentReqDto;
import com.unionclass.paymentservice.domain.payment.dto.out.RequestPaymentResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentFacadeImpl implements PaymentFacade {

    private final OrderService orderService;
    private final PaymentService paymentService;

    @Transactional
    @Override
    public RequestPaymentResDto createOrderAndRequestPayment(CreateOrderAndRequestPaymentReqDto dto) {

        try {

            return paymentService.requestPayment(
                    RequestPaymentReqDto.of(
                            orderService.createOrder(CreateOrderReqDto.of(dto)),
                            dto
                    )
            );
        } catch (Exception e) {

            log.warn("주문 생성 및 결제 요청 실패 - memberUuid: {}, orderName: {}, message: {}",
                    dto.getMemberUuid(), dto.getOrderName(), e.getMessage(), e);

            throw new BaseException(ErrorCode.FAILED_TO_CREATE_ORDER_AND_REQUEST_PAYMENT);
        }
    }
}
