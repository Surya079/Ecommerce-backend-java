package com.ecom.orderservice.openfeign;

import com.ecom.orderservice.controller.dto.PurchaseRequestDto;
import com.ecom.orderservice.exception.BusinessException;
import com.ecom.orderservice.openfeign.dto.PurchaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductClient {

    @Value("${application.config.product-url}")
    private String productUrl;
    private final RestTemplate restTemplate;

    public List<PurchaseResponse> purchaseProducts(List<PurchaseRequestDto> requestDtos){
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<List<PurchaseRequestDto>> requestEntity = new HttpEntity<>(requestDtos, httpHeaders);

        ParameterizedTypeReference<List<PurchaseResponse>> responseType = new ParameterizedTypeReference<>(){};

        ResponseEntity<List<PurchaseResponse>> responseEntity =  restTemplate.exchange(
                productUrl + "/purchase",
                HttpMethod.POST,
                requestEntity,
                responseType

                );

        if(responseEntity.getStatusCode().isError()){
            throw new BusinessException(
                    "An error occurred processing the products purchase: " + responseEntity.getStatusCode()
            );
        }
        return responseEntity.getBody();
    }
}
