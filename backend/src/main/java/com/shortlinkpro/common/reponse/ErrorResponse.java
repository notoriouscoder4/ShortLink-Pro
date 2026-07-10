package com.shortlinkpro.common.reponse;

import java.time.LocalDateTime;

public record ErrorResponse(
        boolean success,
        String message,
        String errorCode,
        LocalDateTime timeStamp
) {
    public static ErrorResponse of(String message, String errorCode) {
        return new ErrorResponse(
                false,
                message,
                errorCode,
                LocalDateTime.now()
        );
    }
}
