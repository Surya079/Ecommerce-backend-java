package com.ecom.orderservice.controller.dto;

public record OrderLineResponseDto(
        Integer id,
        double quantity
) {
}
