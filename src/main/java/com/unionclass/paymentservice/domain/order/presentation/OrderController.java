package com.unionclass.paymentservice.domain.order.presentation;

import com.unionclass.paymentservice.common.response.BaseResponseEntity;
import com.unionclass.paymentservice.common.response.ResponseMessage;
import com.unionclass.paymentservice.domain.order.application.OrderService;
import com.unionclass.paymentservice.domain.order.dto.in.CreateOrderReqDto;
import com.unionclass.paymentservice.domain.order.vo.in.CreateOrderReqVo;
import com.unionclass.paymentservice.domain.order.vo.out.CreateOrderResVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
@Tag(name = "order")
public class OrderController {

    private final OrderService orderService;

    /**
     * /api/v1/order
     *
     * 1. 주문 생성
     */

    /**
     * 1. 주문 생성
     *
     * @param memberUuid
     * @param createOrderReqVo
     * @return
     */
    @Operation(
            summary = "주문 생성",
            hidden = true,
            description = """
                    사용자가 포인트 충전을 위한 주문을 생성하는 API 입니다.
                    충전할 포인트와 보너스 포인트를 기반으로 주문명이 생성되며,
                    생성된 주문 ID 와 주문명, 결제 금액은 결제 모듈에서 사용됩니다.
                    
                    [요청 헤더]
                    - X-Member-UUID : (필수 입력, String) 주문을 요청하는 회원의 UUID
                    
                    [요청 바디]
                    - amount : (필수 입력, Long) 결제 금액 (단위 : 원)
                    - chargePoint : (필수 입력) 충전 포인트
                    - bonusPoint : (선택 입력) 추가 보너스 포인트
                    
                    [응답 필드]
                    - orderId : 생성된 주문 고유 ID
                    - orderName : 결제 화면에 표시될 주문명
                    - amount : 결제 금액 (단위 : 원)
                    
                    [처리 방식]
                    - 주문 ID 는 내부 UUID 생성기를 통해 자동 생성됩니다.
                    - 주문명은 충전 포인트와 보너스 포인트 정보를 기반으로 구성됩니다.
                    - 주문 상태는 초기값으로 PENDING 상태로 설정됩니다.
                    
                    [예외 상황]
                    - FAILED_TO_CREATE_ORDER : 주문 생성 중 알 수 없는 오류 발생
                    """
    )
    @PostMapping
    public BaseResponseEntity<CreateOrderResVo> createOrder(
            @RequestHeader("X-Member-UUID") String memberUuid,
            @RequestBody CreateOrderReqVo createOrderReqVo
    ) {
        return new BaseResponseEntity<>(
                ResponseMessage.SUCCESS_CREATE_ORDER.getMessage(),
                orderService.createOrder(CreateOrderReqDto.of(memberUuid, createOrderReqVo)).toVo());
    }
}
