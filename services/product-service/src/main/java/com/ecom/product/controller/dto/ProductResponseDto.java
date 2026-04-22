package com.ecom.product.controller.dto;

import java.math.BigDecimal;

public record ProductResponseDto (
         Integer id,
         String name,
         String description,
         BigDecimal price,
         double available_quantity,
         Integer categoryId,
         String categoryName,
         String categoryDescription
){
}
