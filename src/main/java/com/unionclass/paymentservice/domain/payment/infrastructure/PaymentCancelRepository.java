package com.unionclass.paymentservice.domain.payment.infrastructure;

import com.unionclass.paymentservice.domain.payment.entity.PaymentCancel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentCancelRepository extends JpaRepository<PaymentCancel, Long> {
}
