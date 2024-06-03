package com.kakaopaysec.liverankapi.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class APIResponse<T> {
    private String message;
    private HttpStatus status;
    private T data;
    private int nextOffset;
}
