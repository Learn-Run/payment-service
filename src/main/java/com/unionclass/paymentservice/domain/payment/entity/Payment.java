package com.unionclass.paymentservice.domain.payment.entity;

import com.unionclass.paymentservice.common.entity.BaseEntity;
import com.unionclass.paymentservice.domain.payment.enums.PaymentStatus;
import com.unionclass.paymentservice.domain.payment.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseEntity {

    @Comment("결제 ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("결제 UUID")
    @Column(nullable = false, unique = true)
    private Long uuid;

    @Comment("멤버 UUID")
    @Column(nullable = false)
    private String memberUuid;

    @Comment("주문 ID")
    @Column(nullable = false, unique = true)
    private String orderId;

    @Comment("주문명")
    @Column(nullable = false)
    private String orderName;

    @Comment("결제방법")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Comment("결제금액")
    @Column(nullable = false)
    private Long amount;

    @Comment("결제상태")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Comment("결제키")
    @Column(nullable = false)
    private String paymentKey;

    @Comment("결제실패코드")
    private String failCode;

    @Comment("결제실패사유")
    private String failReason;

    @Comment("요청일시")
    private LocalDateTime requestedAt;

    @Comment("승인일시")
    private LocalDateTime approvedAt;

    @Comment("취소일시")
    private LocalDateTime canceledAt;

    @Builder
    public Payment(
            Long id, Long uuid, String memberUuid, String orderId, String orderName, PaymentMethod paymentMethod,
            Long amount, PaymentStatus paymentStatus, String paymentKey, String failCode, String failReason,
            LocalDateTime requestedAt, LocalDateTime approvedAt, LocalDateTime canceledAt) {
        this.id = id;
        this.uuid = uuid;
        this.memberUuid = memberUuid;
        this.orderId = orderId;
        this.orderName = orderName;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.paymentKey = paymentKey;
        this.failCode = failCode;
        this.failReason = failReason;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
        this.canceledAt = canceledAt;
    }

    public void recordFail(String failCode, String failReason) {
        this.paymentStatus = PaymentStatus.ABORTED;
        this.failCode = failCode;
        this.failReason = failReason;
        this.canceledAt = LocalDateTime.now();
    }

    public void approvePayment(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
    }
}
