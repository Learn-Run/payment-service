package com.unionclass.paymentservice.common.kafka.event;

import com.unionclass.paymentservice.domain.order.dto.out.GetMemberPointResDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentCreatedEvent {

    private String memberUuid;
    private Long point;

    @Builder
    public PaymentCreatedEvent(String memberUuid, Long point) {
        this.memberUuid = memberUuid;
        this.point = point;
    }

    public static PaymentCreatedEvent from(GetMemberPointResDto dto) {

        return PaymentCreatedEvent.builder()
                .memberUuid(dto.getMemberUuid())
                .point(dto.getTotalPoint())
                .build();
    }
}
