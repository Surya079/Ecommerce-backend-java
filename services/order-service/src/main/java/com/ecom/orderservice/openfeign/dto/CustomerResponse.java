package com.ecom.orderservice.openfeign.dto;

public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email
) {
}
