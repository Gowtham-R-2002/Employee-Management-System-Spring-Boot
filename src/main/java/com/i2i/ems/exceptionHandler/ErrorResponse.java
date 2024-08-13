package com.i2i.ems.exceptionHandler;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
public class ErrorResponse {
    private int statusCode;
    private String message;

    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}