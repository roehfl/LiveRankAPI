package com.kakaopaysec.liverankapi.dto;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public class ErrorResponse {
    private String message;
    private HttpStatus status;
    private String error;
    private String path;
}
