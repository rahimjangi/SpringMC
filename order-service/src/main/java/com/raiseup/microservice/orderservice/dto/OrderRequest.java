package com.raiseup.microservice.orderservice.dto;

import lombok.*;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderRequest {
    private List<OrderLineItemsDto>orderLineItems;

}
