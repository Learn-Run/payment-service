package com.unionclass.paymentservice.domain.payment.infrastructure;

import com.unionclass.paymentservice.domain.payment.entity.PaymentFailure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentFailureRepository extends JpaRepository<PaymentFailure, Long> {
}
