package com.unionclass.paymentservice.domain.infrastructure;

import com.unionclass.paymentservice.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
