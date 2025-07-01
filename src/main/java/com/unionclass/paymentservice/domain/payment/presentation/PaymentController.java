package com.unionclass.paymentservice.domain.payment.presentation;

import com.unionclass.paymentservice.common.response.BaseResponseEntity;
import com.unionclass.paymentservice.common.response.CursorPage;
import com.unionclass.paymentservice.common.response.ResponseMessage;
import com.unionclass.paymentservice.domain.payment.application.PaymentFacade;
import com.unionclass.paymentservice.domain.payment.application.PaymentService;
import com.unionclass.paymentservice.domain.payment.dto.in.*;
import com.unionclass.paymentservice.domain.payment.dto.out.ConfirmPaymentResDto;
import com.unionclass.paymentservice.domain.payment.vo.in.CancelPaymentReqVo;
import com.unionclass.paymentservice.domain.payment.vo.in.ConfirmPaymentReqVo;
import com.unionclass.paymentservice.domain.payment.vo.in.CreateOrderAndRequestPaymentReqVo;
import com.unionclass.paymentservice.domain.payment.vo.in.RequestPaymentReqVo;
import com.unionclass.paymentservice.domain.payment.vo.out.GetPaymentDetailsResVo;
import com.unionclass.paymentservice.domain.payment.vo.out.GetPaymentSummaryResVo;
import com.unionclass.paymentservice.domain.payment.vo.out.GetPaymentUuidResVo;
import com.unionclass.paymentservice.domain.payment.vo.out.RequestPaymentResVo;
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
    private final PaymentFacade paymentFacade;

    /**
     * /api/v1/payment
     *
     * 1. 결제 요청
     * 2. 결제 승인
     * 3. 결제 취소 (환불)
     * 4. 결제 상세 정보 단건 조회 (paymentKey)
     * 5. 결제 상세 정보 단건 조회 (orderId)
     * 6. 결제 UUID 전체 페이지 조회
     * 7. 결제 요약 정보 단건 조회
     * 8. 주문 생성 및 결제 요청
     */

    /**
     * 1. 결제 요청
     *
     * @param memberUuid
     * @param requestPaymentReqVo
     * @return
     */
    @Operation(
            summary = "결제 요청",
            hidden = true,
            description = """
                    사용자의 포인트 결제를 위한 초기 요청을 처리하는 API 입니다.
                    내부적으로 TossPayments 결제 API 를 호출하여 결제창 URL 을 생성하고 반환합니다.
    
                    [요청 헤더]
                    - X-Member-UUID : (String) 결제를 요청하는 회원의 UUID
                    
                    [요청 바디]
                    - orderId : (String) 주문 고유 번호
                    - orderName : (String) 주문명
                    - paymentMethod : (String) 결제방법 (카드, 계좌이체, 휴대폰결제, 가상계좌)
                    - amount : (Long) 결제금액
                    
                    [처리 방식]
                    - 요청된 정보를 바탕으로 TossPayments 결제 API 를 호출
                    - Toss 에서 반환한 checkoutUrl 을 응답으로 반환
                    
                    [응답 필드]
                    - checkoutUrl : (String) 결제창으로 이동할 수 있는 URL
                    
                    [예외 상황]
                    - FAILED_TO_REQUEST_PAYMENT : Toss API 호출 또는 응답 처리 중 오류 발생
                    """
    )
    @PostMapping("/request")
    public BaseResponseEntity<RequestPaymentResVo> requestPayment(
            @RequestHeader("X-Member-UUID") String memberUuid,
            @RequestBody RequestPaymentReqVo requestPaymentReqVo
    ) {
        return new BaseResponseEntity<>(
                ResponseMessage.SUCCESS_REQUEST_PAYMENT.getMessage(),
                paymentService.requestPayment(RequestPaymentReqDto.of(memberUuid, requestPaymentReqVo)).toVo());
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
                    - amount : (Long) 결제 금액
                    
                    [처리 방식]
                    - TossPayments 의 결제 승인 API ('/confirm/') 을 호출하여 결제 완료 여부를 확인
                    - 승인 성공 시, 결제 정보 (Payment) 를 DB 에 저장하고, 결제 상태를 'DONE' (결제완료) 으로 변경
                    - 승인 실패 시, 실패 코드 및 메세지를 저장하고 반환
                    
                    [예외 상황]
                    - TOSS_EMPTY_RESPONSE : Toss API 의 응답 값이 없는 경우
                    - TOSS_API_CALL_FAILED : Toss API 호출 중 오류 발생
                    """
    )
    @PostMapping("/confirm")
    public BaseResponseEntity<Void> confirmPayment(
            @RequestHeader("X-Member-UUID") String memberUuid,
            @RequestBody ConfirmPaymentReqVo confirmPaymentReqVo
    ) {
        paymentFacade.confirmPayment(ConfirmPaymentReqDto.of(memberUuid, confirmPaymentReqVo));

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
            summary = "결제 취소",
            description = """
                    사용자가 완료한 결제를 취소하는 API 입니다.
                    TossPayments 의 결제 취소 API 를 호출하고, 응답받은 취소 정보를 저장합니다
                    
                    [요청 헤더]
                    - X-Member-UUID : (String) 결제를 요청한 회원의 UUID
                    
                    [요청 경로]
                    - paymentKey: (String) Toss 에서 결제 완료 시 제공된 결제 키
                    
                    [요청 바디]
                    - cancelReason : (String, 필수입력) 취소 사유
                    
                    [처리 방식]
                    - paymentKey 로 기존 결제 정보를 조회
                    - TossPayments 의 결제 취소 API ('/{paymentKey}/cancel')를 호출하여 실제 환불 처리 수행
                    - 결제 상태를 CANCELED (결제취소) 로 변경
                    - 결제 취소 정보 저장
                    
                    [예외 상황]
                    - FAILED_TO_CANCEL_PAYMENT : 결제 취소 중 알 수 없는 오류 발생
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

    /**
     * 4. 결제 상세 정보 단건 조회 (paymentKey)
     *
     * @param memberUuid
     * @param paymentKey
     * @return
     */
    @Operation(
            summary = "결제 상세 정보 단건 조회 (paymentKey)",
            description = """
                    TossPayments 의 결제 Key (paymentKey) 를 기반으로 결제 상세 정보를 조회하는 API 입니다.
                    내부적으로 TossPayments API 를 호출하여 실시간 결제 정보를 가져오며,
                    성공 시 전체 결제 상태 및 결제 수단, 금액 등의 정보를 반환합니다.
                    
                    [요청 헤더]
                    - X-Member-UUID : (String) 회원 UUID
                    
                    [요청 경로]
                    - paymentKey : (String) TossPayments 에서 제공한 결제 고유 키
                    
                    [처리 방식]
                    - TossPayments API 로 GET 요청을 보내 결제 상세 데이터를 수신
                    - 수신된 JSON 응답을 내부 DTO 로 변환하여 반환
                    
                    [응답 필드]
                    - mId : 토스 페이먼츠에서 발급하는 상점 아이디(MID)
                    - lastTransactionKey : 마지막 거래의 키 값
                    - paymentKey : 토스에서 결제를 식별하는 키 값, 결제 데이터 관리를 위해 반드시 저장해야 함
                    - orderId : 결제 요청에서 상점이 직접 생성한 주문 번호
                    - orderName : 구매하려는 상품명
                    - taxExemptionAmount : 과세를 제외한 결제 금액
                    - status : 결제 처리 상태
                    - requestedAt : 결제 요청 일시
                    - approvedAt : 결제 승인 일시
                    - useEscrow : 에스크로 사용 여부 (에스크로 : 고객이 제품을 잘 받은 뒤에 상점에게 돈을 지급하는 시스템)
                    - cultureExpense : 문화비 지출 여부 (계좌이체, 가상계좌 결제에만 적용됨)
                    - `card` : 카드로 결제하면 제공되는 카드 관련 정보
                        * issuerCode : 카드 발급사 두 자리 코드
                        * acquirerCode : 카드 매입사 두 자리 코드
                        * number : 카드 번호 (번호의 일부는 마스킹되어 있음)
                        * installmentPlanMonths : 할부 개월 수 (일시불이면 0)
                        * isInterestFree : 무이자 할부의 적용 여부
                        * interestPayer : 부가 적용된 결제에서 할부 수수료를 부담하는 주체 (BUYER, CARD_COMPANY, MERCHANT)
                        * approveNo : 카드사 승인 번호
                        * useCardPoint : 카드사 포인트 사용 여부
                        * cardType : 카드 종류 (신용, 체크, 기프트, 미확인)
                        * ownerType : 카드의 소유자 타입 (개인, 법인, 미확인)
                        * acquireStatus : 카드 결제의 매입 상태 (READY, REQUESTED, COMPLETED, CANCEL_REQUESTED, CANCELED)
                        * amount : 카드사에 결제 요청한 금액
                    - `virtualAccount` : 가상계좌로 결제하면 제공되는 가상계좌 관련 정보
                        * accountType : 가상 계좌 타입 (일반, 고정)
                        * accountNumber : 발급된 계좌번호
                        * bankCode : 가상계좌 은행 두 자리 코드
                        * customerName : 가상계좌를 발급한 구매자명
                        * dueDate : 입금 기한 (yyyy-MM-dd 'T' HH:mm:ss)
                        * refundStatus : 환불 처리 상태 (NONE, PENDING, FAILED, PARTIAL_FAILED, COMPLETED)
                        * expired : 가상계좌의 만료 여부
                        * settlementStatus : 정산 상태 (INCOMPLETED, COMPLETED)
                        * refundReceiveAccount : 구매자의 환불 계좌 정보 (은행코드, 계좌번호, 예금주 정보)
                    - `transfer` : 계좌 이체로 결제했을 때의 이체 정보
                        * bankCode : 은행 두 자리 코드
                        * settlementStatus : 정산 상태 (INCOMPLETED, COMPLETED)
                    - `mobilePhone` : 휴대폰으로 결제하면 제공되는 휴대폰 결제 관련 정보
                        * customerMobilePhone : 구매자가 결제에 사용한 휴대폰 번호
                        * settlementStatus : 정산 상태 (INCOMPLETED, COMPLETED)
                        * receiptUrl : 휴대폰 결제 내역 영수증을 확인할 수 있는 주소
                    - `giftCertificate` : 상품권으로 결제하면 제공되는 상품권 결제 관련 정보
                        * approveNo : 결제 승인번호
                        * settlementStatus : 정산 상태 (INCOMPLETED, COMPLETED)
                    - `cashReceipt` : 현금 영수증 정보
                        * type : 현금영수증의 종류 (소득공제, 지출증빙)
                        * receiptKey : 현금영수증의 키 값
                        * issueNumber : 현금영수증 발급 번호
                        * receiptUrl : 발행된 현금영수증을 확인할 수 있는 주소
                        * amount : 현금영수증 처리된 금액
                        * taxFreeAmount : 면세 처리된 금액
                    - `cashReceipts` : 현금영수증 발행 및 취소 이력이 담기는 배열 (순서 보장 X)
                        * receiptKey : 현금영수증의 키 값
                        * orderId : 결제 요청에서 상점이 직접 생성한 주문 번호
                        * orderName : 주문명
                        * type : 현금영수증의 종류 (소득공제, 지출증빙)
                        * issueNumber : 현금영수증 발급 번호
                        * receiptUrl : 발행된 현금영수증을 확인할 수 있는 주소
                        * businessNumber : 현금영수증을 발급한 사업자 등록 번호
                        * transactionType : 현금영수증 발급 종류 (CONFIRM, CANCEL)
                        * amount : 현금영수증 처리된 금액
                        * taxFreeAmount : 면세 처리된 금액
                        * issueStatus : 현금영수증 발급 상태 (IN_PROGRESS, COMPLETED, FAILED)
                        * failure : 실패 객체 (code, message)
                        * customerIdentityNumber : 현금영수증 발급에 필요한 소비자 인증 수단
                        * requestedAt : 현금영수증 발급 혹은 취소를 요청한 일시
                    - `discount` : 카드사의 즉시 할인 프로모션 정보
                        * amount : 할인 프로모션을 적용한 결제 금액
                    - `cancels` : 결제 취소 이력
                        * orderId : 주문명
                        * cancelAmount : 결제를 취소한 금액
                        * cancelStatus : 취소 상태 (DONE : 결제가 성공적으로 취소된 상태)
                        * cancelReason : 결제를 취소한 이유
                        * canceledAt : 결제 취소가 일어난 날짜와 시간 정보
                    - secret : 웹훅을 검증하는 최대 50자 값
                    - type : 결제 타입 (NORMAL, BILLING, BRANDPAY)
                    - `easyPay` : 간편 결제 정보
                        * provider : 선택한 간편 결제사 코드
                        * amount : 간편 결제 서비스에 등록된 계좌 혹은 현금성 포인트로 결제한 금액
                        * discountAmount : 간편 결제 서비스의 적립 포인트나 쿠폰 등으로 즉시 할인된 금액
                    - country : 결제 국가
                    - `failure` : 결제 승인에 실패하면 응답으로 받는 에러 객체
                        * code : 실패 코드
                        * message : 실패 메시지
                    - isPartialCancelable : 부분 취소 가능 여부 (false 이면 전액 취소만 가능)
                    - `receipt` : 발행된 영수증 정보
                        * url : 구매자에게 제공할 수 있는 결제 수단별 영수증
                    - `checkout` : 결제창 정보
                        * url : 결제창이 열리는 주소
                    - totalAmount : 총 결제 금액
                    - balanceAmount : 취소할 수 있는 금액(잔고)
                    - suppliedAmount : 공급가액
                    - vat : 부가세
                    - taxFreeAmount : 면세 금액
                    - metadata : SDK 에서 직접 추가할 수 있는 결제 관련 정보
                    - version : 날짜 기반의 응답 버전
                    
                    [예외 상황]
                    - FAILED_TO_GET_PAYMENT_DETAILS : Toss API 호출 실패 또는 응답 처리 중 예외 발생
                    """
    )
    @GetMapping("/{paymentKey}")
    public BaseResponseEntity<GetPaymentDetailsResVo> getPaymentDetailsByPaymentKey(
            @RequestHeader("X-Member-UUID") String memberUuid,
            @PathVariable String paymentKey
    ) {
        return new BaseResponseEntity<>(
                ResponseMessage.SUCCESS_GET_PAYMENT_DETAILS_BY_PAYMENT_KEY.getMessage(),
                paymentService.getPaymentDetailsByPaymentKey(GetPaymentKeyReqDto.of(memberUuid, paymentKey)).toVo());
    }

    /**
     * 5. 결제 상세 정보 단건 조회 (orderId)
     *
     * @param memberUuid
     * @param orderId
     * @return
     */
    @Operation(
            summary = "결제 상세 정보 단건 조회 (orderId)",
            description = """
                    TossPayments 의 결제 Key (orderId) 를 기반으로 결제 상세 정보를 조회하는 API 입니다.
                    내부적으로 TossPayments API 를 호출하여 실시간 결제 정보를 가져오며,
                    성공 시 전체 결제 상태 및 결제 수단, 금액 등의 정보를 반환합니다.
                    
                    [요청 헤더]
                    - X-Member-UUID : (String) 회원 UUID
                    
                    [요청 경로]
                    - paymentKey : (String) TossPayments 에서 제공한 결제 고유 키
                    
                    [처리 방식]
                    - TossPayments API 로 GET 요청을 보내 결제 상세 데이터를 수신
                    - 수신된 JSON 응답을 내부 DTO 로 변환하여 반환
                    
                    [응답 필드]
                    - mId : 토스 페이먼츠에서 발급하는 상점 아이디(MID)
                    - lastTransactionKey : 마지막 거래의 키 값
                    - paymentKey : 토스에서 결제를 식별하는 키 값, 결제 데이터 관리를 위해 반드시 저장해야 함
                    - orderId : 결제 요청에서 상점이 직접 생성한 주문 번호
                    - orderName : 구매하려는 상품명
                    - taxExemptionAmount : 과세를 제외한 결제 금액
                    - status : 결제 처리 상태
                    - requestedAt : 결제 요청 일시
                    - approvedAt : 결제 승인 일시
                    - useEscrow : 에스크로 사용 여부 (에스크로 : 고객이 제품을 잘 받은 뒤에 상점에게 돈을 지급하는 시스템)
                    - cultureExpense : 문화비 지출 여부 (계좌이체, 가상계좌 결제에만 적용됨)
                    - `card` : 카드로 결제하면 제공되는 카드 관련 정보
                        * issuerCode : 카드 발급사 두 자리 코드
                        * acquirerCode : 카드 매입사 두 자리 코드
                        * number : 카드 번호 (번호의 일부는 마스킹되어 있음)
                        * installmentPlanMonths : 할부 개월 수 (일시불이면 0)
                        * isInterestFree : 무이자 할부의 적용 여부
                        * interestPayer : 부가 적용된 결제에서 할부 수수료를 부담하는 주체 (BUYER, CARD_COMPANY, MERCHANT)
                        * approveNo : 카드사 승인 번호
                        * useCardPoint : 카드사 포인트 사용 여부
                        * cardType : 카드 종류 (신용, 체크, 기프트, 미확인)
                        * ownerType : 카드의 소유자 타입 (개인, 법인, 미확인)
                        * acquireStatus : 카드 결제의 매입 상태 (READY, REQUESTED, COMPLETED, CANCEL_REQUESTED, CANCELED)
                        * amount : 카드사에 결제 요청한 금액
                    - `virtualAccount` : 가상계좌로 결제하면 제공되는 가상계좌 관련 정보
                        * accountType : 가상 계좌 타입 (일반, 고정)
                        * accountNumber : 발급된 계좌번호
                        * bankCode : 가상계좌 은행 두 자리 코드
                        * customerName : 가상계좌를 발급한 구매자명
                        * dueDate : 입금 기한 (yyyy-MM-dd 'T' HH:mm:ss)
                        * refundStatus : 환불 처리 상태 (NONE, PENDING, FAILED, PARTIAL_FAILED, COMPLETED)
                        * expired : 가상계좌의 만료 여부
                        * settlementStatus : 정산 상태 (INCOMPLETED, COMPLETED)
                        * refundReceiveAccount : 구매자의 환불 계좌 정보 (은행코드, 계좌번호, 예금주 정보)
                    - `transfer` : 계좌 이체로 결제했을 때의 이체 정보
                        * bankCode : 은행 두 자리 코드
                        * settlementStatus : 정산 상태 (INCOMPLETED, COMPLETED)
                    - `mobilePhone` : 휴대폰으로 결제하면 제공되는 휴대폰 결제 관련 정보
                        * customerMobilePhone : 구매자가 결제에 사용한 휴대폰 번호
                        * settlementStatus : 정산 상태 (INCOMPLETED, COMPLETED)
                        * receiptUrl : 휴대폰 결제 내역 영수증을 확인할 수 있는 주소
                    - `giftCertificate` : 상품권으로 결제하면 제공되는 상품권 결제 관련 정보
                        * approveNo : 결제 승인번호
                        * settlementStatus : 정산 상태 (INCOMPLETED, COMPLETED)
                    - `cashReceipt` : 현금 영수증 정보
                        * type : 현금영수증의 종류 (소득공제, 지출증빙)
                        * receiptKey : 현금영수증의 키 값
                        * issueNumber : 현금영수증 발급 번호
                        * receiptUrl : 발행된 현금영수증을 확인할 수 있는 주소
                        * amount : 현금영수증 처리된 금액
                        * taxFreeAmount : 면세 처리된 금액
                    - `cashReceipts` : 현금영수증 발행 및 취소 이력이 담기는 배열 (순서 보장 X)
                        * receiptKey : 현금영수증의 키 값
                        * orderId : 결제 요청에서 상점이 직접 생성한 주문 번호
                        * orderName : 주문명
                        * type : 현금영수증의 종류 (소득공제, 지출증빙)
                        * issueNumber : 현금영수증 발급 번호
                        * receiptUrl : 발행된 현금영수증을 확인할 수 있는 주소
                        * businessNumber : 현금영수증을 발급한 사업자 등록 번호
                        * transactionType : 현금영수증 발급 종류 (CONFIRM, CANCEL)
                        * amount : 현금영수증 처리된 금액
                        * taxFreeAmount : 면세 처리된 금액
                        * issueStatus : 현금영수증 발급 상태 (IN_PROGRESS, COMPLETED, FAILED)
                        * failure : 실패 객체 (code, message)
                        * customerIdentityNumber : 현금영수증 발급에 필요한 소비자 인증 수단
                        * requestedAt : 현금영수증 발급 혹은 취소를 요청한 일시
                    - `discount` : 카드사의 즉시 할인 프로모션 정보
                        * amount : 할인 프로모션을 적용한 결제 금액
                    - `cancels` : 결제 취소 이력
                        * orderId : 주문명
                        * cancelAmount : 결제를 취소한 금액
                        * cancelStatus : 취소 상태 (DONE : 결제가 성공적으로 취소된 상태)
                        * cancelReason : 결제를 취소한 이유
                        * canceledAt : 결제 취소가 일어난 날짜와 시간 정보
                    - secret : 웹훅을 검증하는 최대 50자 값
                    - type : 결제 타입 (NORMAL, BILLING, BRANDPAY)
                    - `easyPay` : 간편 결제 정보
                        * provider : 선택한 간편 결제사 코드
                        * amount : 간편 결제 서비스에 등록된 계좌 혹은 현금성 포인트로 결제한 금액
                        * discountAmount : 간편 결제 서비스의 적립 포인트나 쿠폰 등으로 즉시 할인된 금액
                    - country : 결제 국가
                    - `failure` : 결제 승인에 실패하면 응답으로 받는 에러 객체
                        * code : 실패 코드
                        * message : 실패 메시지
                    - isPartialCancelable : 부분 취소 가능 여부 (false 이면 전액 취소만 가능)
                    - `receipt` : 발행된 영수증 정보
                        * url : 구매자에게 제공할 수 있는 결제 수단별 영수증
                    - `checkout` : 결제창 정보
                        * url : 결제창이 열리는 주소
                    - totalAmount : 총 결제 금액
                    - balanceAmount : 취소할 수 있는 금액(잔고)
                    - suppliedAmount : 공급가액
                    - vat : 부가세
                    - taxFreeAmount : 면세 금액
                    - metadata : SDK 에서 직접 추가할 수 있는 결제 관련 정보
                    - version : 날짜 기반의 응답 버전
                    
                    [예외 상황]
                    - FAILED_TO_GET_PAYMENT_DETAILS : Toss API 호출 실패 또는 응답 처리 중 예외 발생
                    """
    )
    @GetMapping("/order/{orderId}")
    public BaseResponseEntity<GetPaymentDetailsResVo> getPaymentDetailsByOrderId(
            @RequestHeader("X-Member-UUID") String memberUuid,
            @PathVariable String orderId
    ) {
        return new BaseResponseEntity<>(
                ResponseMessage.SUCCESS_GET_PAYMENT_DETAILS_BY_ORDER_ID.getMessage(),
                paymentService.getPaymentDetailsByOrderId(GetOrderIdReqDto.of(memberUuid, orderId)).toVo()
        );
    }

    @GetMapping("/uuid/all")
    public CursorPage<GetPaymentUuidResVo> getAllPaymentUuids(
            @RequestHeader("X-Member-UUID") String memberUuid,
            @RequestParam String cursor,
            @RequestParam String direction,
            @RequestParam(defaultValue = "8") int size
    ) {
        paymentService.getAllPaymentUuids(CursorPageParamReqDto.of(memberUuid, cursor, direction, size));
        return null;
    }

    /**
     * 7. 결제 요약 정보 단건 조회
     *
     * @param memberUuid
     * @param paymentUuid
     * @return
     */
    @Operation(
            summary = "결제 요약 정보 단건 조회",
            description = """
                    외부 API 호출 없이 내부 DB 에 저장된 데이터를 바탕으로 결제 요약 정보를 반환합니다.
                    
                    [요청 헤더]
                    - X-Member-UUID : (String) 회원 UUID
                    
                    [요청 경로]
                    - paymentUuid: (Long) 결제 UUID
                    
                    [처리 방식]
                    - paymentUuid 를 기준으로 결제 정보를 조회
                    - 결제 정보가 존재하지 않으면 예외 처리
                    
                    [응답 필드]
                    - orderId : (String) 주문 ID
                    - orderName : (String) 주문명
                    - paymentKey : (String) 결제 고유 키
                    - paymentMethod : (String) 결제 방법 (카드, 계좌이체, 휴대폰결제, 가상계좌)
                    - paymentStatus : (String) 결제 처리 상태
                    - totalAmount : 총 결제 금액
                    - suppliedAmount : 공급가액
                    - vat : 부가세
                    - currency : 결제 통화 (KRW)
                    - requestedAt : 결제 요청 일시
                    - approvedAt : 결제 승인 일시
                    
                    [예외 상황]
                    - FAILED_TO_FIND_PAYMENT_BY_PAYMENT_UUID : 해당 UUID 에 대한 결제 요약 정보를 찾을 수 없는 경우
                    - FAILED_TO_GET_PAYMENT_SUMMARY : 결제 요약 정보 조회 중 알 수 없는 오류 발생
                    """
    )
    @GetMapping("/{paymentUuid}/summary")
    public BaseResponseEntity<GetPaymentSummaryResVo> getPaymentSummary(
            @RequestHeader("X-Member-UUID") String memberUuid,
            @PathVariable Long paymentUuid
    ) {
        return new BaseResponseEntity<>(
                ResponseMessage.SUCCESS_GET_PAYMENT_SUMMARY.getMessage(),
                paymentService.getPaymentSummary(GetPaymentSummaryReqDto.of(memberUuid, paymentUuid)).toVo()
        );
    }

    @Operation(
            summary = "주문 생성 및 결제 요청",
            description = """
                    [요청 필드]
                    - orderName : 주문명
                    - point : 포인트
                    - bonusPoint : 보너스 포인트
                    - paymentAmount : 결제 금액
                    - paymentMethod : 카드 로 입력
                    """
    )
    @PostMapping("/order/request")
    public BaseResponseEntity<RequestPaymentResVo> createOrderAndRequestPayment(
            @RequestHeader("X-Member-UUID") String memberUuid,
            @RequestBody CreateOrderAndRequestPaymentReqVo vo
    ) {
        return new BaseResponseEntity<>(
                ResponseMessage.SUCCESS_CREATE_ORDER_AND_REQUEST_PAYMENT.getMessage(),
                paymentFacade.createOrderAndRequestPayment(CreateOrderAndRequestPaymentReqDto.of(memberUuid, vo)).toVo()
        );
    }

}
