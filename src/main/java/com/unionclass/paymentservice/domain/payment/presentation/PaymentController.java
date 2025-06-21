package com.unionclass.paymentservice.domain.payment.presentation;

import com.unionclass.paymentservice.common.response.BaseResponseEntity;
import com.unionclass.paymentservice.common.response.ResponseMessage;
import com.unionclass.paymentservice.domain.payment.application.PaymentService;
import com.unionclass.paymentservice.domain.payment.dto.in.CancelPaymentReqDto;
import com.unionclass.paymentservice.domain.payment.dto.in.ConfirmPaymentReqDto;
import com.unionclass.paymentservice.domain.payment.dto.in.CreatePaymentReqDto;
import com.unionclass.paymentservice.domain.payment.vo.in.CancelPaymentReqVo;
import com.unionclass.paymentservice.domain.payment.vo.in.ConfirmPaymentReqVo;
import com.unionclass.paymentservice.domain.payment.vo.in.CreatePaymentReqVo;
import com.unionclass.paymentservice.domain.payment.vo.out.CreatePaymentResVo;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(
            summary = "결제 생성",
            description = """
                    사용자가 포인트에 대한 결제를 시작하는 API 입니다.
                    이 API 는 TossPayments 의 결제 생성 API 를 호출하여,
                    결제창 URL (checkoutUrl) 을 반환합니다.
                    프론트엔드는 해당 URL 로 Redirect 하여 결제 진행을 유도할 수 있습니다.
                    
                    [요청 헤더]
                    - X-Member-UUID : (String) 결제를 요청하는 회원의 UUID
                    
                    [요청 바디]
                    - orderId : (String) 주문 고유 번호
                    - orderName : (String) 주문명
                    - paymentMethod : (String) 결제방법 (카드, 계좌이체, 휴대폰결제, 가상계좌)
                    - amount : (Long) 결제금액
                    
                    [처리 방식]
                    - 요청된 정보를 바탕으로 TossPayments 결제 API 를 호출
                    - 주문 ID, 주문명, 결제 금액, 결제방법, 성공/실패 Redirection URL, 유효시간 정보를 전달
                    - Toss 에서 반환한 checkoutUrl 을 응답으로 반환
                    
                    [응답 필드]
                    - orderId : (String) 주문 고유 번호
                    - checkoutUrl : (String) 결제창으로 이동할 수 있는 URL
                    
                    [예외 상황]
                    - TOSS_EMPTY_RESPONSE : Toss API 의 응답 값이 없는 경우
                    - TOSS_API_CALL_FAILED : Toss API 호출 중 오류 발생
                    - INVALID_PAYMENT_METHOD : 허용되지 않은 결제 수단이 요청된 경우
                    """
    )
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
    @Operation(
            summary = "결제 승인",
            description = """
                    사용자의 결제 완료를 위해 결제 정보를 승인 및 저장하는 API 입니다.
                    TossPayments 로부터 결제 승인을 요청하고, 승인 성공 시 내부 결제 정보를 저장합니다.
                    
                    [요청 헤더]
                    - X-Member-UUID : (String) 결제를 요청한 회원의 UUID
                    
                    [요청 바디]
                    - paymentKey : (String) Toss 에서 결제 완료 시 제공하는 결제 키
                    - orderId : (String) 주문 고유 번호
                    - orderName : (String) 주문명
                    - paymentMethod : (String) 결제방법 (카드, 계좌이체, 휴대폰결제, 가상계좌)
                    - amount : (Long) 결제 금액
                    
                    [처리 방식]
                    - TossPayments 의 결제 승인 API ('/confirm/') 을 호출하여 결제 완료 여부를 확인
                    - 승인 성공 시, 결제 정보 (Payment) 를 DB 에 저장하고, 결제 상태를 'DONE' (결제완료) 으로 변경
                    - 승인 실패 시, 실패 코드 및 메세지를 저장하고 예외 처리
                    
                    [예외 상황]
                    - TOSS_EMPTY_RESPONSE : Toss API 의 응답 값이 없는 경우
                    - TOSS_PAYMENT_REJECTED : 한도 초과, 인증 실패 등으로 결제가 거절되었거나 실패한 경우
                    - TOSS_API_CALL_FAILED : Toss API 호출 중 오류 발생
                    """
    )
    @PostMapping("/confirm")
    public BaseResponseEntity<CreatePaymentResVo> confirmPayment(
            @RequestHeader("X-Member-UUID") String memberUuid,
            @RequestBody ConfirmPaymentReqVo confirmPaymentReqVo
    ) {
        paymentService.confirmPayment(ConfirmPaymentReqDto.of(memberUuid, confirmPaymentReqVo));
        return new BaseResponseEntity<>(ResponseMessage.SUCCESS_CONFIRM_PAYMENT.getMessage());
    }

    /**
     * 3. 결제 취소 (환불)
     *
     * @param memberUuid
     * @param paymentKey
     * @param cancelPaymentReqVo
     * @return
     */
    @Operation(
            summary = "결제 취소 (환불)",
            description = """
                    사용자가 완료한 결제를 취소(환불)하는 API 입니다.
                    내부적으로 환불 이력을 저장한 후,
                    TossPayments 의 결제 취소 API 를 호출하여
                    환불 처리를 수행합니다.
                    
                    [요청 헤더]
                    - X-Member-UUID : (String) 결제를 요청한 회원의 UUID
                    
                    [요청 경로]
                    - paymentKey: (String) Toss 에서 결제 완료 시 제공된 결제 키
                    
                    [요청 바디]
                    - cancelReason : (String, 필수입력) 환불(취소) 사유
                    
                    [처리 방식]
                    - paymentKey 로 기존 결제 정보를 조회
                    - 결제 상태를 CANCELED (결제취소) 로 변경
                    - 환불 이력을 저장
                    - TossPayments 의 결제 취소 API ('/{paymentKey}/cancel')를 호출하여 실제 환불 처리 수행
                    - Toss 응답 상태가 정상(2xx)인 경우 환불 성공으로 간주
                    
                    [예외 상황]
                    - FAILED_TO_FIND_PAYMENT_BY_PAYMENT_KEY : paymentKey 로 결제 내역을 찾을 수 없는 경우
                    - FAILED_TO_SAVE_PAYMENT_AND_REFUND_HISTORY : 결제 정보 변경 및 환불 정보 저장 중 예외 발생
                    - FAILED_TO_CALL_TOSS_API_FOR_REFUND : 결제 취소에 대한 Toss API 호출 중 오류 발생
                    """
    )
    @PostMapping("/{paymentKey}/cancel")
    public BaseResponseEntity<Void> cancelPayment(
            @RequestHeader("X-Member-UUID") String memberUuid,
            @PathVariable String paymentKey,
            @RequestBody CancelPaymentReqVo cancelPaymentReqVo
    ) {
        paymentService.cancelPayment(CancelPaymentReqDto.of(memberUuid, paymentKey, cancelPaymentReqVo));
        return new BaseResponseEntity<>(ResponseMessage.SUCCESS_CANCEL_PAYMENT.getMessage());
    }
}
