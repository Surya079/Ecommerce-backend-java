package com.ecom.product.exception;

import java.util.Map;

public record ProductErrorResponseDto(
        Map<String, String> errors
) {
}
