package com.ecom.customerservice.mapper;

import com.ecom.customerservice.contoller.dto.CustomerRequestDto;
import com.ecom.customerservice.contoller.dto.CustomerResponseDto;
import com.ecom.customerservice.entity.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapping {

    public Customer toCustomer(CustomerRequestDto requestDto){

        if(requestDto == null) return null;

        return Customer.builder()
                .id(requestDto.id())
                .firstName(requestDto.firstName())
                .lastName(requestDto.lastName())
                .email(requestDto.email())
                .address(requestDto.address())
                .build();
    }

    public CustomerResponseDto fromCustomer(Customer customer) {
        return new CustomerResponseDto(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getAddress()
        );
    }
}
