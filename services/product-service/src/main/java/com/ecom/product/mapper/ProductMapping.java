package com.ecom.product.mapper;

import com.ecom.product.controller.dto.ProductPurchaseResponseDto;
import com.ecom.product.controller.dto.ProductRequestDto;
import com.ecom.product.controller.dto.ProductResponseDto;
import com.ecom.product.entity.Category;
import com.ecom.product.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapping {

    public Product toProduct(ProductRequestDto requestDto) {
        return Product.builder()
                .id(requestDto.id())
                .name(requestDto.name())
                .description(requestDto.description())
                .price(requestDto.price())
                .available_quantity(requestDto.available_quantity())
                .category(
                        Category.builder()
                                .id(requestDto.categoryId())
                                .build())
                .build();
    }

    public ProductResponseDto fromProduct(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getAvailable_quantity(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCategory().getDescription()
        );
    }

    public ProductPurchaseResponseDto toProductPurchaseResponse(Product product, double quantity) {

        return new ProductPurchaseResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getAvailable_quantity()
        );
    }
}
