package com.unionclass.paymentservice.domain.payment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPaymentFailure is a Querydsl query type for PaymentFailure
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPaymentFailure extends EntityPathBase<PaymentFailure> {

    private static final long serialVersionUID = -1103538716L;

    public static final QPaymentFailure paymentFailure = new QPaymentFailure("paymentFailure");

    public final com.unionclass.paymentservice.common.entity.QBaseEntity _super = new com.unionclass.paymentservice.common.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath failCode = createString("failCode");

    public final StringPath failReason = createString("failReason");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath memberUuid = createString("memberUuid");

    public final StringPath orderId = createString("orderId");

    public final StringPath paymentKey = createString("paymentKey");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> uuid = createNumber("uuid", Long.class);

    public QPaymentFailure(String variable) {
        super(PaymentFailure.class, forVariable(variable));
    }

    public QPaymentFailure(Path<? extends PaymentFailure> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPaymentFailure(PathMetadata metadata) {
        super(PaymentFailure.class, metadata);
    }

}

