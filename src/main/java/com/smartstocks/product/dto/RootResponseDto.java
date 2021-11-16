package com.smartstocks.product.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RootResponseDto<T> {

    private int statusCode;
    private HttpStatus status;
    private String message;
    private LocalDateTime timeStamp;
    private Map<String, String> errors;
    private T data;
}
