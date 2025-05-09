package com.example.projectc1023i1.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class HandlerRuntimeException extends RuntimeException {
    public HandlerRuntimeException(String message) {
        super(message);
    }
}
