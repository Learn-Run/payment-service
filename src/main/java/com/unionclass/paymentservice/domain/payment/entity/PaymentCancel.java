package com.unionclass.paymentservice.domain.payment.entity;

import com.unionclass.paymentservice.common.entity.BaseEntity;
import com.unionclass.paymentservice.domain.payment.enums.CancelStatus;
import com.unionclass.paymentservice.domain.payment.enums.RefundProcessStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "payment_cancel")
public class PaymentCancel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("환불기록 UUID")
    @Column(nullable = false, unique = true)
    private Long uuid;

    @Comment("멤버 UUID")
    @Column(nullable = false)
    private String memberUuid;

    @Comment("결제 고유 키")
    @Column(nullable = false)
    private String paymentKey;

    @Comment("주문 ID")
    @Column(nullable = false)
    private String orderId;

    @Comment("취소 금액")
    @Column(nullable = false)
    private Long amount;

    @Comment("취소 사유")
    @Column(nullable = false)
    private String cancelReason;

    @Comment("취소 상태")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CancelStatus cancelStatus;

    @Comment("취소 일시")
    private ZonedDateTime canceledAt;

    @Builder
    public PaymentCancel(
            Long id, Long uuid, String memberUuid, String paymentKey, String orderId,
            Long amount, String cancelReason, CancelStatus cancelStatus, ZonedDateTime canceledAt
    ) {
        this.id = id;
        this.uuid = uuid;
        this.memberUuid = memberUuid;
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.amount = amount;
        this.cancelReason = cancelReason;
        this.cancelStatus = cancelStatus;
        this.canceledAt = canceledAt;
    }
}
