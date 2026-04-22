package com.ecom.customerservice.contoller;

import com.ecom.customerservice.contoller.dto.CustomerRequestDto;
import com.ecom.customerservice.contoller.dto.CustomerResponseDto;
import com.ecom.customerservice.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    public ResponseEntity<String> createCustomer(
            @RequestBody @Valid CustomerRequestDto requestDto
            ){
        return ResponseEntity.ok(service.createCustomer(requestDto));
    }

    @PutMapping("/{customer-id}")
    public ResponseEntity<Void> updateCustomer(
            @RequestBody @Valid CustomerRequestDto requestDto,
            @PathVariable("customer-id") String customerId
    ){
        System.out.println("Customer ID: " + customerId);
        service.updateCustomer(requestDto, customerId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getCustomers(){
       return  ResponseEntity.ok(service.findAllCustomers());
    }

    @GetMapping("/exists/{customer-id}")
    public ResponseEntity<Boolean> isCustomerExist(
            @PathVariable("customer-id") String customerId
    ){
        return ResponseEntity.ok(service.isCustomerById(customerId));
    }

    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(
            @PathVariable("customer-id") String customerId
    ){
        return ResponseEntity.ok(service.getCustomerById(customerId));
    }

    @DeleteMapping("{customer-id}")
    public ResponseEntity<Void> deleteCustomerById(
            @PathVariable("customer-id") String customerId
    ){
        service.deleteCustomerById(customerId);
        return ResponseEntity.accepted().build();
    }


}
