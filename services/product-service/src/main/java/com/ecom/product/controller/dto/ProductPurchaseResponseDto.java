package com.ecom.product.controller.dto;

import java.math.BigDecimal;

public record ProductPurchaseResponseDto(

        Integer productId,
        String name,
        String description,
        BigDecimal price,
        double quantity
) {
}
