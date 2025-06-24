package com.unionclass.paymentservice.domain.order.application;

import com.unionclass.paymentservice.domain.order.dto.in.CreateOrderReqDto;
import com.unionclass.paymentservice.domain.order.dto.out.CreateOrderResDto;

public interface OrderService {

    CreateOrderResDto createOrder(CreateOrderReqDto dto);
}
