package com.unionclass.paymentservice.domain.order.dto.out;

import com.unionclass.paymentservice.domain.order.entity.Orders;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetMemberPointResDto {

    private String memberUuid;
    private Long totalPoint;

    @Builder
    public GetMemberPointResDto(String memberUuid, Long totalPoint) {
        this.memberUuid = memberUuid;
        this.totalPoint = totalPoint;
    }

    public static GetMemberPointResDto from(Orders order) {

        return GetMemberPointResDto.builder()
                .memberUuid(order.getMemberUuid())
                .totalPoint(order.getTotalPoint())
                .build();
    }
}
