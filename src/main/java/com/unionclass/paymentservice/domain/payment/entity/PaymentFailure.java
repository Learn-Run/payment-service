package com.unionclass.paymentservice.domain.payment.entity;

import com.unionclass.paymentservice.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "payment_failure")
public class PaymentFailure extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("결제 실패 UUID")
    @Column(nullable = false, unique = true)
    private Long uuid;

    @Comment("회원 UUID")
    @Column(nullable = false, length = 36)
    private String memberUuid;

    @Comment("결제 고유 키")
    @Column(nullable = false, unique = true)
    private String paymentKey;

    @Comment("주문 ID")
    @Column(nullable = false)
    private String orderId;

    @Comment("실패 코드")
    @Column(nullable = false)
    private String failCode;

    @Comment("실패 사유")
    @Column(nullable = false)
    private String failReason;

    @Builder
    public PaymentFailure(
            Long id, Long uuid, String memberUuid, String paymentKey,
            String orderId, String failCode, String failReason
    ) {
        this.id = id;
        this.uuid = uuid;
        this.memberUuid = memberUuid;
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.failCode = failCode;
        this.failReason = failReason;
    }
}
