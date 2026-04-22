package com.ecom.orderservice.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequestDto (

        @NotNull(message = "Product is mandatory")
        Integer productId,

        @Positive(message = "quantity should be at least 1")
        double quantity
){

}
