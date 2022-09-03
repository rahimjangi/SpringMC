package com.raiseup.microservice.orderservice.controller;

import com.raiseup.microservice.orderservice.dto.OrderRequest;
import com.raiseup.microservice.orderservice.dto.OrderResponse;
import com.raiseup.microservice.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-service")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody OrderRequest orderRequest){
        log.info(orderRequest.toString());
        return orderService.save(orderRequest);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse>getOrders(){
        return orderService.getOrders();
    }

}
