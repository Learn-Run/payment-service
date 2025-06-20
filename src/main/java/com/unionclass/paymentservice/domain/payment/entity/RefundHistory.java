package com.unionclass.paymentservice.domain.payment.entity;

import com.unionclass.paymentservice.common.entity.BaseEntity;
import com.unionclass.paymentservice.domain.payment.enums.RefundProcessStatus;
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
public class RefundHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("환불기록 UUID")
    @Column(nullable = false, unique = true)
    private Long uuid;

    @Comment("결제 UUID")
    @Column(nullable = false, unique = true)
    private Long paymentUuid;

    @Comment("멤버 UUID")
    @Column(nullable = false)
    private String memberUuid;

    @Comment("주문 ID")
    @Column(nullable = false)
    private String orderId;

    @Comment("결제키")
    @Column(nullable = false)
    private String paymentKey;

    @Comment("환불사유")
    @Column(nullable = false)
    private String failReason;

    @Comment("실패코드")
    private String failCode;

    @Comment("환불처리상태")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RefundProcessStatus refundProcessStatus;

    @Comment("요청일시")
    private LocalDateTime requestedAt;

    @Comment("승인일시")
    private LocalDateTime approvedAt;

    @Builder
    public RefundHistory(
            Long id, Long uuid, Long paymentUuid, String memberUuid, String orderId,
            String paymentKey, String failReason, String failCode, RefundProcessStatus refundProcessStatus,
            LocalDateTime requestedAt, LocalDateTime approvedAt
    ) {
        this.id = id;
        this.uuid = uuid;
        this.paymentUuid = paymentUuid;
        this.memberUuid = memberUuid;
        this.orderId = orderId;
        this.paymentKey = paymentKey;
        this.failReason = failReason;
        this.failCode = failCode;
        this.refundProcessStatus = refundProcessStatus;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
    }
}
