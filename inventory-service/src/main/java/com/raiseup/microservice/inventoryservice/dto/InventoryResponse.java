package com.raiseup.microservice.inventoryservice.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class InventoryResponse {
    private String skuCode;
    private Boolean isInStock;
}
