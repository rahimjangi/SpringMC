package com.raiseup.microservice.orderservice.repository;

import com.raiseup.microservice.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
