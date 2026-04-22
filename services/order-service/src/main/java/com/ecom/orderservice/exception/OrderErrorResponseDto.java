package com.ecom.orderservice.exception;

import java.util.Map;

public record OrderErrorResponseDto(
        Map<String, String> errors
) {
}
