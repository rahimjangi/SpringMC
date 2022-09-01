package com.raiseup.microservice.orderservice.controller;

import com.raiseup.microservice.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order-service")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private final OrderService orderService;

}