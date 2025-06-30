package com.unionclass.paymentservice.domain.payment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPayment is a Querydsl query type for Payment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPayment extends EntityPathBase<Payment> {

    private static final long serialVersionUID = -175691578L;

    public static final QPayment payment = new QPayment("payment");

    public final com.unionclass.paymentservice.common.entity.QBaseEntity _super = new com.unionclass.paymentservice.common.entity.QBaseEntity(this);

    public final DateTimePath<java.time.ZonedDateTime> approvedAt = createDateTime("approvedAt", java.time.ZonedDateTime.class);

    public final StringPath country = createString("country");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath currency = createString("currency");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isPartialCancelable = createBoolean("isPartialCancelable");

    public final StringPath memberUuid = createString("memberUuid");

    public final EnumPath<com.unionclass.paymentservice.domain.payment.enums.Method> method = createEnum("method", com.unionclass.paymentservice.domain.payment.enums.Method.class);

    public final StringPath orderId = createString("orderId");

    public final StringPath orderName = createString("orderName");

    public final StringPath paymentKey = createString("paymentKey");

    public final DateTimePath<java.time.ZonedDateTime> requestedAt = createDateTime("requestedAt", java.time.ZonedDateTime.class);

    public final EnumPath<com.unionclass.paymentservice.domain.payment.enums.Status> status = createEnum("status", com.unionclass.paymentservice.domain.payment.enums.Status.class);

    public final NumberPath<Long> suppliedAmount = createNumber("suppliedAmount", Long.class);

    public final NumberPath<Long> taxFreeAmount = createNumber("taxFreeAmount", Long.class);

    public final NumberPath<Long> totalAmount = createNumber("totalAmount", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> uuid = createNumber("uuid", Long.class);

    public final NumberPath<Long> vat = createNumber("vat", Long.class);

    public QPayment(String variable) {
        super(Payment.class, forVariable(variable));
    }

    public QPayment(Path<? extends Payment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPayment(PathMetadata metadata) {
        super(Payment.class, metadata);
    }

}

