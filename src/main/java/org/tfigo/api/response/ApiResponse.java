package org.tfigo.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record ApiResponse <T> (
        @JsonProperty("success") boolean success,
        @JsonProperty("message") String message,
        @JsonProperty("data") T data,
        @JsonProperty("timestamp") LocalDateTime timestamp
        ) {

    public static <T> ApiResponse<T> info(String message) {
        return new ApiResponse<>(true, message, null, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, null, data, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null, LocalDateTime.now());
    }
}
