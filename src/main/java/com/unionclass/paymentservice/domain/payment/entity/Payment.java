package com.unionclass.paymentservice.domain.payment.entity;

import com.unionclass.paymentservice.common.entity.BaseEntity;
import com.unionclass.paymentservice.domain.payment.enums.Method;
import com.unionclass.paymentservice.domain.payment.enums.Status;
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

    @Comment("결제 고유 키")
    @Column(nullable = false)
    private String paymentKey;

    @Comment("결제 수단")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Method method;

    @Comment("결제 상태")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Comment("총 결제 금액")
    @Column(nullable = false)
    private Long totalAmount;

    @Comment("공급가액")
    private Long suppliedAmount;

    @Comment("부가세")
    private Long vat;

    @Comment("비과세 금액")
    private Long taxFreeAmount;

    @Comment("결제 국가")
    private String country;

    @Comment("통화")
    private String currency;

    @Comment("결제 요청 일시")
    private ZonedDateTime requestedAt;

    @Comment("결제 승인 일시")
    private ZonedDateTime approvedAt;

    @Comment("부분 취소 여부")
    private boolean isPartialCancelable;

    @Builder
    public Payment(
            Long id, Long uuid, String memberUuid, String orderId, String orderName, String paymentKey,
            Method method, Status status, Long totalAmount, Long suppliedAmount,
            Long vat, Long taxFreeAmount, String country, String currency, ZonedDateTime requestedAt,
            ZonedDateTime approvedAt, boolean isPartialCancelable
    ) {
        this.id = id;
        this.uuid = uuid;
        this.memberUuid = memberUuid;
        this.orderId = orderId;
        this.orderName = orderName;
        this.paymentKey = paymentKey;
        this.method = method;
        this.status = status;
        this.totalAmount = totalAmount;
        this.suppliedAmount = suppliedAmount;
        this.vat = vat;
        this.taxFreeAmount = taxFreeAmount;
        this.country = country;
        this.currency = currency;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
        this.isPartialCancelable = isPartialCancelable;
    }
}
