package com.cashlog.cashlog.controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@RestControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Map<String, String>> handleInvalidFormatException(InvalidFormatException ex) {
        String fieldName = "";
        String invalidValue = "";
    
        if (ex.getPath() != null && !ex.getPath().isEmpty()) {
            fieldName = ex.getPath().get(0).getFieldName();
            invalidValue = ex.getValue().toString();
        }
    
        String[] validValues = new String[0];
    
        Class<?> targetType = ex.getTargetType();
        if (targetType != null && targetType.isEnum()) {
            Object[] enumConstants = targetType.getEnumConstants();
            validValues = Arrays.stream(enumConstants)
                                .map(Object::toString)
                                .toArray(String[]::new);
        }
    
        String message = String.format("Invalid value '%s' for field '%s'. Expected one of: %s",
                invalidValue,
                fieldName,
                Arrays.toString(validValues)
        );
    
        Map<String, String> response = new HashMap<>();
        response.put("error", message);
    
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
