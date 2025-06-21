package com.unionclass.paymentservice.domain.payment.infrastructure;

import com.unionclass.paymentservice.domain.payment.entity.RefundHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundHistoryRepository extends JpaRepository<RefundHistory, Long> {
}
