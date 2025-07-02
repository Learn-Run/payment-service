package com.unionclass.paymentservice.domain.payment.infrastructure;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.unionclass.paymentservice.common.response.CursorPage;
import com.unionclass.paymentservice.common.util.CursorEncoder;
import com.unionclass.paymentservice.domain.payment.dto.in.CursorPageParamReqDto;
import com.unionclass.paymentservice.domain.payment.dto.out.GetPaymentUuidResDto;
import com.unionclass.paymentservice.domain.payment.entity.QPayment;
import com.unionclass.paymentservice.domain.payment.enums.CursorDirection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PaymentCustomRepositoryImpl implements PaymentCustomRepository {

    private final CursorEncoder cursorEncoder;

    private final JPAQueryFactory queryFactory;

    QPayment qPayment = QPayment.payment;

    @Override
    public CursorPage<GetPaymentUuidResDto> findPaymentUuidsByCursor(CursorPageParamReqDto dto) {

        BooleanExpression predicate = buildPredicate(dto);

        List<GetPaymentUuidResDto> results = queryFactory
                .select(
                        Projections.constructor(
                                GetPaymentUuidResDto.class,
                                qPayment.uuid,
                                qPayment.createdAt
                        ))
                .from(qPayment)
                .where(predicate)
                .orderBy(
                        qPayment.createdAt.desc(),
                        qPayment.uuid.desc())
                .limit(dto.getSize() + 1)
                .fetch();

        boolean hasNext = results.size() > dto.getSize();

        if (hasNext) {
            results = results.subList(0, dto.getSize());
        }

        String nextCursor = hasNext
                ? cursorEncoder.encodeCursor(
                results.get(results.size() - 1).getCreatedAt(), results.get(results.size() - 1).getPaymentUuid())
                : null;

        return CursorPage.of(
                results, nextCursor, null, hasNext, null, dto.getSize(), null, null
        );
    }

    private BooleanExpression buildPredicate(CursorPageParamReqDto dto) {

        BooleanExpression predicate = qPayment.memberUuid.eq(dto.getMemberUuid());

        if (dto.getCreatedAtCursor() != null && dto.getUuidCursor() != null) {

            LocalDateTime createdAt = LocalDateTime.parse(dto.getCreatedAtCursor());
            Long uuid = dto.getUuidCursor();

            predicate = predicate.and(
                    qPayment.createdAt
                            .lt(createdAt)
                            .or(qPayment.createdAt
                                    .eq(createdAt)
                                    .and(qPayment.uuid.lt(uuid)))
            );
        }

        // startDate 조건 (inclusive)
        if (dto.getStartDate() != null) {
            predicate = predicate.and(qPayment.createdAt.goe(dto.getStartDate().atStartOfDay()));
        }

        // endDate 조건 (inclusive)
        if (dto.getEndDate() != null) {
            predicate = predicate.and(qPayment.createdAt.loe(dto.getEndDate().atTime(23, 59, 59)));
        }

        return predicate;
    }
}
