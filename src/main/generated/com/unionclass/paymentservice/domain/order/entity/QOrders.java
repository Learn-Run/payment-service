package com.unionclass.paymentservice.domain.order.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrders is a Querydsl query type for Orders
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrders extends EntityPathBase<Orders> {

    private static final long serialVersionUID = -554561875L;

    public static final QOrders orders = new QOrders("orders");

    public final com.unionclass.paymentservice.common.entity.QBaseEntity _super = new com.unionclass.paymentservice.common.entity.QBaseEntity(this);

    public final NumberPath<Long> amount = createNumber("amount", Long.class);

    public final NumberPath<Long> bonusPoint = createNumber("bonusPoint", Long.class);

    public final NumberPath<Long> chargePoint = createNumber("chargePoint", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath id = createString("id");

    public final StringPath memberUuid = createString("memberUuid");

    public final StringPath name = createString("name");

    public final EnumPath<com.unionclass.paymentservice.domain.order.enums.OrderStatus> status = createEnum("status", com.unionclass.paymentservice.domain.order.enums.OrderStatus.class);

    public final NumberPath<Long> totalPoint = createNumber("totalPoint", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QOrders(String variable) {
        super(Orders.class, forVariable(variable));
    }

    public QOrders(Path<? extends Orders> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrders(PathMetadata metadata) {
        super(Orders.class, metadata);
    }

}

