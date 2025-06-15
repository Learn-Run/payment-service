package com.unionclass.paymentservice.domain.payment.entity;

import com.unionclass.paymentservice.common.entity.BaseEntity;
import com.unionclass.paymentservice.domain.payment.enums.PaymentStatus;
import com.unionclass.paymentservice.domain.payment.enums.PaymentType;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("결제 UUID")
    @Column(nullable = false, unique = true)
    private Long uuid;

    @Comment("결제 금액")
    @Column(nullable = false)
    private Long amount;

    @Comment("회원 UUID")
    @Column(nullable = false, length = 36)
    private String memberUuid;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private LocalDateTime approvedAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Comment("주문명")
    @Column(nullable = false)
    private String orderName;

    @Builder
    public Payment(
            Long id, Long uuid, Long amount, String memberUuid, PaymentStatus paymentStatus,
            LocalDateTime approvedAt, PaymentType paymentType, String orderName
    ) {
        this.id = id;
        this.uuid = uuid;
        this.amount = amount;
        this.memberUuid = memberUuid;
        this.paymentStatus = paymentStatus;
        this.approvedAt = approvedAt;
        this.paymentType = paymentType;
        this.orderName = orderName;
    }
}
