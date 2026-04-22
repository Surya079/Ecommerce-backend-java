package com.ecom.notificationservice.kafka.order.dto;

public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email
){
}
