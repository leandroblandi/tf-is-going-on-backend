package org.tfigo.api.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.tfigo.api.exception.ResourceAlreadyExistsException;
import org.tfigo.api.exception.ResourceNotFoundException;
import org.tfigo.api.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<String>> handleResourceAlreadyExistsException(ResourceAlreadyExistsException e) {
        ApiResponse<String> response = ApiResponse.error(e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleResourceNotFoundException(ResourceNotFoundException e) {
        ApiResponse<String> response = ApiResponse.error(e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<String>> handleRuntimeException(RuntimeException e) {
        ApiResponse<String> response = ApiResponse.error(e.getMessage());
        return ResponseEntity.status(500).body(response);
    }
}