package com.unionclass.paymentservice.domain.order.application;

import com.unionclass.paymentservice.common.exception.BaseException;
import com.unionclass.paymentservice.common.exception.ErrorCode;
import com.unionclass.paymentservice.common.util.StringUuidGenerator;
import com.unionclass.paymentservice.domain.order.dto.in.CreateOrderReqDto;
import com.unionclass.paymentservice.domain.order.dto.in.UpdateOrderStatusReqDto;
import com.unionclass.paymentservice.domain.order.dto.out.CreateOrderResDto;
import com.unionclass.paymentservice.domain.order.entity.Orders;
import com.unionclass.paymentservice.domain.order.enums.OrderStatus;
import com.unionclass.paymentservice.domain.order.infrastructure.OrderRepository;
import com.unionclass.paymentservice.domain.order.util.OrderNameTemplateProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

            log.info("주문 생성 성공 - orderId: {}, memberUuid: {}", dto.getMemberUuid(), orders.getMemberUuid());

            return CreateOrderResDto.from(orders);

        } catch (Exception e) {

            log.warn("주문 생성 실패 - memberUuid: {}, message: {}", dto.getMemberUuid(), e.getMessage(), e);

            throw new BaseException(ErrorCode.FAILED_TO_CREATE_ORDER);

        }
    }

    @Transactional
    @Override
    public void updateOrderStatus(UpdateOrderStatusReqDto dto) {

        try {

            orderRepository.save(
                    dto.toEntity(
                            orderRepository.findById(dto.getOrderId())
                                    .orElseThrow(() -> new BaseException(ErrorCode.FAILED_TO_FIND_ORDER)
                                    )
                    )
            );

            log.warn("주문 상태 변경 성공 - orderId: {}", dto.getOrderId());

        } catch (BaseException e) {

            throw e;

        } catch (Exception e) {

            log.warn("주문 상태 변경 실패 - orderId: {}, message: {}", dto.getOrderId(), e.getMessage(), e);

            throw new BaseException(ErrorCode.FAILED_TO_UPDATE_ORDER_STATUS);
        }
    }
}
