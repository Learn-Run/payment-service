package com.unionclass.paymentservice.domain.payment.infrastructure;

import com.unionclass.paymentservice.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long>, PaymentCustomRepository {

    Optional<Payment> findByUuid(Long paymentUuid);

}
