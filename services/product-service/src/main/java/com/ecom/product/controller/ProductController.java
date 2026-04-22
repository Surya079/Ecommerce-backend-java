package com.ecom.product.controller;

import com.ecom.product.controller.dto.ProductPurchaseRequestDto;
import com.ecom.product.controller.dto.ProductPurchaseResponseDto;
import com.ecom.product.controller.dto.ProductRequestDto;
import com.ecom.product.controller.dto.ProductResponseDto;
import com.ecom.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @PostMapping
    public ResponseEntity<Integer> createProduct(
            @RequestBody @Valid ProductRequestDto requestDto
    ){
        return ResponseEntity.ok(service.createProduct(requestDto));
    }

    @PostMapping("/purchase")
    public ResponseEntity<ArrayList<ProductPurchaseResponseDto>> purchaseProduct(
            @RequestBody ArrayList<ProductPurchaseRequestDto> requestList
    ){
        return ResponseEntity.ok(service.purchaseProducts(requestList));

    }


    @GetMapping("/{product-id}")
    public ResponseEntity<ProductResponseDto> getProductById(
            @PathVariable("product-id") Integer productId
    ){
        return ResponseEntity.ok(service.getProductById(productId));
    }


    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(){
        return ResponseEntity.ok(service.getAllProducts());
    }
}
