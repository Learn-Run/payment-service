package com.unionclass.paymentservice.domain.order.application;

import com.unionclass.paymentservice.common.exception.BaseException;
import com.unionclass.paymentservice.common.exception.ErrorCode;
import com.unionclass.paymentservice.common.util.StringUuidGenerator;
import com.unionclass.paymentservice.domain.order.dto.in.CreateOrderReqDto;
import com.unionclass.paymentservice.domain.order.dto.out.CreateOrderResDto;
import com.unionclass.paymentservice.domain.order.entity.Orders;
import com.unionclass.paymentservice.domain.order.infrastructure.OrderRepository;
import com.unionclass.paymentservice.domain.order.util.OrderNameTemplateProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderNameTemplateProvider templateProvider;
    private final StringUuidGenerator uuidGenerator;

    @Transactional
    @Override
    public CreateOrderResDto createOrder(CreateOrderReqDto dto) {

        try {

            String orderId = uuidGenerator.generate();
            String orderName = templateProvider.getOrderNameTemplate(dto.getChargePoint(), dto.getBonusPoint());

            Orders orders = dto.toEntity(orderId, orderName);

            orderRepository.save(orders);

            log.info("주문 생성 성공 - orderId: {}, memberUuid: {}", orders.getId(), orders.getMemberUuid());

            return CreateOrderResDto.from(orders);

        } catch (Exception e) {

            log.warn("주문 생성 실패 - memberUuid: {}, message: {}", dto.getMemberUuid(), e.getMessage(), e);

            throw new BaseException(ErrorCode.FAILED_TO_CREATE_ORDER);

        }
    }
}
