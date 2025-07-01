package com.unionclass.paymentservice.domain.order.application;

import com.unionclass.paymentservice.domain.order.dto.in.CreateOrderReqDto;
import com.unionclass.paymentservice.domain.order.dto.in.UpdateOrderStatusReqDto;
import com.unionclass.paymentservice.domain.order.dto.out.CreateOrderResDto;
import com.unionclass.paymentservice.domain.order.dto.out.GetMemberPointResDto;

public interface OrderService {

    CreateOrderResDto createOrder(CreateOrderReqDto dto);

    void updateOrderStatus(UpdateOrderStatusReqDto dto);

    GetMemberPointResDto getMemberPointInfo(String orderId);
}
