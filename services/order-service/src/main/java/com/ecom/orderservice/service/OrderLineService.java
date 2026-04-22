package com.ecom.orderservice.service;

import com.ecom.orderservice.controller.dto.OrderLineRequest;
import com.ecom.orderservice.mapper.OrderLineMapper;
import com.ecom.orderservice.repository.OrderLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper mapper;

    public Integer saveOrderLine(OrderLineRequest orderLineRequest){
        var order = mapper.toOrderLine(orderLineRequest);
        return orderLineRepository.save(order).getId();
    }
}
