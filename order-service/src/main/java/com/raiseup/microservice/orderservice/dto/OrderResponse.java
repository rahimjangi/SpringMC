package com.raiseup.microservice.orderservice.dto;

import lombok.*;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderResponse {
    private List<OrderLineItemsDto> orderLineItems;
}
