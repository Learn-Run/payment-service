package com.unionclass.paymentservice.domain.payment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPaymentCancel is a Querydsl query type for PaymentCancel
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPaymentCancel extends EntityPathBase<PaymentCancel> {

    private static final long serialVersionUID = -814082336L;

    public static final QPaymentCancel paymentCancel = new QPaymentCancel("paymentCancel");

    public final com.unionclass.paymentservice.common.entity.QBaseEntity _super = new com.unionclass.paymentservice.common.entity.QBaseEntity(this);

    public final NumberPath<Long> amount = createNumber("amount", Long.class);

    public final DateTimePath<java.time.ZonedDateTime> canceledAt = createDateTime("canceledAt", java.time.ZonedDateTime.class);

    public final StringPath cancelReason = createString("cancelReason");

    public final EnumPath<com.unionclass.paymentservice.domain.payment.enums.CancelStatus> cancelStatus = createEnum("cancelStatus", com.unionclass.paymentservice.domain.payment.enums.CancelStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath memberUuid = createString("memberUuid");

    public final StringPath orderId = createString("orderId");

    public final StringPath paymentKey = createString("paymentKey");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> uuid = createNumber("uuid", Long.class);

    public QPaymentCancel(String variable) {
        super(PaymentCancel.class, forVariable(variable));
    }

    public QPaymentCancel(Path<? extends PaymentCancel> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPaymentCancel(PathMetadata metadata) {
        super(PaymentCancel.class, metadata);
    }

}

