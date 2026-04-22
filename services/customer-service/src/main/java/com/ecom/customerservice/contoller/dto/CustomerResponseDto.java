package com.ecom.customerservice.contoller.dto;


import com.ecom.customerservice.entity.Address;

public record CustomerResponseDto(
        String id,
        String firstName,
        String lastName,
        String email,
        Address address

) {
}
