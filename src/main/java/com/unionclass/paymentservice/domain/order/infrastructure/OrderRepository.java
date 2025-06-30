package com.unionclass.paymentservice.domain.order.infrastructure;

import com.unionclass.paymentservice.domain.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, String> {
}
