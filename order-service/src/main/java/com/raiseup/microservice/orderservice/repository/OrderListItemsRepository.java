package com.raiseup.microservice.orderservice.repository;

import com.raiseup.microservice.orderservice.model.OrderLineItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderListItemsRepository extends JpaRepository<OrderLineItems,Long> {
}
