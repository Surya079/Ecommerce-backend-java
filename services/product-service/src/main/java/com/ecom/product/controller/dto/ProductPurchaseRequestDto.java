package com.ecom.product.controller.dto;

import jakarta.validation.constraints.NotNull;

public record ProductPurchaseRequestDto(

        @NotNull(message = "Product is required")
        Integer productId,

        @NotNull(message = "Quantity is required")
        double quantity

) {
}
