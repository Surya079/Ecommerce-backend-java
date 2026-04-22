package com.ecom.product.service;

import com.ecom.product.controller.dto.ProductPurchaseRequestDto;
import com.ecom.product.controller.dto.ProductPurchaseResponseDto;
import com.ecom.product.controller.dto.ProductRequestDto;
import com.ecom.product.controller.dto.ProductResponseDto;
import com.ecom.product.exception.ProductNotFoundException;
import com.ecom.product.exception.ProductPurchaseException;
import com.ecom.product.mapper.ProductMapping;
import com.ecom.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapping mapping;

    public Integer createProduct(ProductRequestDto requestDto){
        var product = mapping.toProduct(requestDto);
        return repository.save(product).getId();
    }


    public ArrayList<ProductPurchaseResponseDto> purchaseProducts(List<ProductPurchaseRequestDto> requestList) {
        var productIds = requestList.stream()
                .map(ProductPurchaseRequestDto::productId)
                .toList();
        var storedProducts = repository.findByIdInOrderById(productIds);

        if (productIds.size() != storedProducts.size()){
            throw new ProductPurchaseException("One or more product does not exist");

        }
        var storedRequest = requestList
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequestDto::productId))
                .toList();
        ArrayList<ProductPurchaseResponseDto> purchaseProducts = new ArrayList<ProductPurchaseResponseDto>();

        for (int i = 0; i < storedProducts.size(); i++){
            var product = storedProducts.get(i);
            var productRequest = storedRequest.get(i);
            if (product.getAvailable_quantity() < productRequest.quantity()){
                throw new ProductPurchaseException("Insufficient stock quantity for product with required product ID::" + productRequest.productId());
            }

            var newAvailableQuanity = product.getAvailable_quantity() - productRequest.quantity();
            product.setAvailable_quantity(newAvailableQuanity);
            repository.save(product);
            purchaseProducts.add(mapping.toProductPurchaseResponse(product, productRequest.quantity()));

        }
        return purchaseProducts;


    }

    public ProductResponseDto getProductById(Integer productId) {
    return repository.findById(productId)
            .map(mapping::fromProduct)
            .orElseThrow(() -> new ProductNotFoundException(
                    String.format("Product Not found with the required product ID:: %s", productId)
            ));
    }

    public List<ProductResponseDto> getAllProducts(){
        return repository.findAll()
                .stream()
                .map(mapping::fromProduct)
                .collect(Collectors.toList());
    }
}
