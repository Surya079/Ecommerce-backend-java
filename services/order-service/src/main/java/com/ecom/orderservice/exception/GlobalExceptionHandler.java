package com.ecom.orderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handle(BusinessException exp){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exp.getMsg());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handle(OrderNotFoundException exp){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exp.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<OrderErrorResponseDto> handle(MethodArgumentNotValidException exp){

        var errors = new HashMap<String, String>();

        exp.getBindingResult().getAllErrors()
                .forEach(error -> {
                    var fieldName = ((FieldError) error).getField();
                    var errorName = error.getDefaultMessage();
                    errors.put(fieldName, errorName);
                });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new OrderErrorResponseDto(errors));

    }
}
