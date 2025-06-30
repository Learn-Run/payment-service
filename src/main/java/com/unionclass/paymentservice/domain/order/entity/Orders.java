package com.unionclass.paymentservice.domain.order.entity;

import com.unionclass.paymentservice.common.entity.BaseEntity;
import com.unionclass.paymentservice.domain.order.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Orders extends BaseEntity {

    @Id
    @Comment("주문 ID")
    @Column(nullable = false, unique = true, length = 36)
    private String id;

    @Comment("주문명")
    @Column(nullable = false)
    private String name;

    @Comment("회원 UUID")
    @Column(nullable = false)
    private String memberUuid;

    @Comment("결제 금액")
    @Column(nullable = false)
    private Long amount;

    @Comment("충전 포인트")
    @Column(nullable = false)
    private Long chargePoint;

    @Comment("보너스 포인트")
    @Column(nullable = false)
    private Long bonusPoint;

    @Comment("총 포인트")
    @Column(nullable = false)
    private Long totalPoint;

    @Comment("주문 상태")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Builder
    public Orders(
            String id, String name, String memberUuid, Long amount,
            Long chargePoint, Long bonusPoint, Long totalPoint, OrderStatus status
    ) {
        this.id = id;
        this.name = name;
        this.memberUuid = memberUuid;
        this.amount = amount;
        this.chargePoint = chargePoint;
        this.bonusPoint = bonusPoint;
        this.totalPoint = totalPoint;
        this.status = status;
    }
}
