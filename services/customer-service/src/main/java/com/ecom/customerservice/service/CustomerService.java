package com.ecom.customerservice.service;

import com.ecom.customerservice.contoller.dto.CustomerRequestDto;
import com.ecom.customerservice.contoller.dto.CustomerResponseDto;
import com.ecom.customerservice.entity.Customer;
import com.ecom.customerservice.exception.CustomerNotFoundException;
import com.ecom.customerservice.mapper.CustomerMapping;
import com.ecom.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapping mapping;

    public String createCustomer(CustomerRequestDto requestDto){

        var customer = repository.save(mapping.toCustomer(requestDto));

        return customer.getId();
    }

    public void updateCustomer(CustomerRequestDto requestDto, String customerId){
        var customer = repository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(
                    String.format("Cannot update customer:: No customer found with provided ID:: %s", requestDto.id())
                ));
        log.info(customerId);
        log.info(String.valueOf(customer));
        mergeCustomer(customer, requestDto);
        repository.save(customer);
    }

    private void mergeCustomer(Customer customer, CustomerRequestDto requestDto) {

        if(StringUtils.isNotBlank(requestDto.firstName())){
            customer.setFirstName(requestDto.firstName());
        }

        if(StringUtils.isNotBlank(requestDto.lastName())){
            customer.setLastName(requestDto.lastName());
        }
        if(StringUtils.isNotBlank(requestDto.email())){
            customer.setEmail(requestDto.email());
        }
        if(requestDto.address() != null){
            customer.setAddress(requestDto.address());
        }
    }

    public List<CustomerResponseDto> findAllCustomers() {
        return repository.findAll()
                .stream()
                .map(mapping::fromCustomer)
                .collect(Collectors.toList());
    }

    public Boolean isCustomerById(String customerId) {
        return repository.findById(customerId).isPresent();
    }

    public CustomerResponseDto getCustomerById(String customerId) {
        return repository.findById(customerId)
                .map(mapping::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("No customer found with provided id:: %s", customerId)
                ));
    }

    public void deleteCustomerById(String customerId) {
        repository.deleteById(customerId);
    }
}
