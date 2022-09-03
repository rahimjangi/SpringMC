package com.raiseup.microservice.orderservice.service;

import com.raiseup.microservice.orderservice.dto.OrderLineItemsDto;
import com.raiseup.microservice.orderservice.dto.OrderRequest;
import com.raiseup.microservice.orderservice.dto.OrderResponse;
import com.raiseup.microservice.orderservice.model.Order;
import com.raiseup.microservice.orderservice.model.OrderLineItems;
import com.raiseup.microservice.orderservice.repository.OrderListItemsRepository;
import com.raiseup.microservice.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    @Autowired
    private final OrderRepository orderRepository;
    private final OrderListItemsRepository orderListItemsRepository;

    public OrderResponse save(OrderRequest orderRequest) {
        Order order = new Order();
        OrderResponse orderResponse = new OrderResponse();
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItems()
                .stream()
                .map(this::mapOrderLineItems)
                .toList();
        order.setOrderLineItemsList(orderLineItems);
        order.setOrderNumber(UUID.randomUUID().toString());
        Order savedOrder = orderRepository.save(order);
        for(int i=0;i<savedOrder.getOrderLineItemsList().size();i++){
            savedOrder.getOrderLineItemsList().get(i).setOrder(order);
        }
        return null;
    }

    private OrderLineItems mapOrderLineItems(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems= new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }

    public List<OrderResponse> getOrders() {
        List<OrderResponse> orderResponseList = orderRepository.findAll().stream().map(this::orderResponseMap).toList();
        return orderResponseList;
    }

    private OrderResponse orderResponseMap(Order order) {
        OrderResponse orderResponse= new OrderResponse();
        orderResponse.setOrderLineItems(order.getOrderLineItemsList().stream().map(this::orderLineItemDtoMapper).toList());
        return orderResponse;
    }

    private OrderLineItemsDto orderLineItemDtoMapper(OrderLineItems orderLineItems) {
        OrderLineItemsDto orderLineItemsDto= new OrderLineItemsDto();
        orderLineItemsDto.setPrice(orderLineItems.getPrice());
        orderLineItemsDto.setQuantity(orderLineItems.getQuantity());
        orderLineItemsDto.setSkuCode(orderLineItems.getSkuCode());
        return orderLineItemsDto;
    }
}
