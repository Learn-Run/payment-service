package com.unionclass.paymentservice.domain.order.util;

import org.springframework.stereotype.Service;

@Service
public class OrderNameTemplateProvider {

    public String getOrderNameTemplate(Long chargePoint, Long bonusPoint) {

        if (bonusPoint != null && bonusPoint > 0) {
            return String.format("포인트 %dP + 보너스 %dP", chargePoint, bonusPoint);
        }
        return String.format("포인트 %dP", chargePoint);
    }
}
