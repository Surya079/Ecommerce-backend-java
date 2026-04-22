package com.ecom.customerservice.exception;

import java.util.Map;

public record CustomerErrorResponseDto (
    Map<String, String > errors
){
}
