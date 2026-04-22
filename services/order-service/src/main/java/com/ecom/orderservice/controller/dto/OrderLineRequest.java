package com.ecom.orderservice.controller.dto;

public record OrderLineRequest(
    Integer id,
    Integer orderId,
    Integer productId,
    double quantity
) {
}
