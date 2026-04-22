package com.ecom.notificationservice.kafka.order.dto;

import java.math.BigDecimal;

public record ProductResponseDto(
        Integer productId,

        String name,

        String description,

        BigDecimal price,

        double quantity

) {
}
