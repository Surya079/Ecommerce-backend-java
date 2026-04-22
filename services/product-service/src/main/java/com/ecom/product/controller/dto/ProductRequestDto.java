package com.ecom.product.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequestDto (

     Integer id,

     @NotNull(message = "Product name is required ")
     String name,

     @NotNull(message = "Product description is required")
     String description,

     @Positive(message = "Price should be positive")
     BigDecimal price,

     @Positive(message = "Product quantity should be positive")
     double available_quantity,

     @NotNull(message = "Product category is required")
     Integer categoryId
){

}
