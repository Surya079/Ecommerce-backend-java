package com.ecom.paymentservice.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record CustomerResponseDto(
        String id,

        @NotNull(message = "First Name is required")
        String firstName,

        @NotNull(message = "Last Name is required")
        String lastName,

        @NotNull(message = "Email Name is required")
        @Email(message = "Email should be valid")
        String email
) {
}
