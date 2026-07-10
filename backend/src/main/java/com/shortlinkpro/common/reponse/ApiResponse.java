package com.shortlinkpro.common.reponse;

import java.time.LocalDateTime;

public record ApiResponse<T>(
        boolean success,
        String message,
        T data,
        LocalDateTime timeStamp
) {
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(
                true,
                message,
                data,
                LocalDateTime.now()
        );
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                true,
                "Request processed successfully",
                data,
                LocalDateTime.now()
        );
    }
}
