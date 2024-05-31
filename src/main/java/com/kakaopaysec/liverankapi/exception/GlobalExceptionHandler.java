package com.kakaopaysec.liverankapi.exception;

import org.springframework.http.HttpStatus;
import com.kakaopaysec.liverankapi.dto.ErrorResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public class GlobalExceptionHandler implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        HttpStatus status;
        String message;

        if (ex instanceof IllegalArgumentException) {
            status = HttpStatus.BAD_REQUEST;
            message = ex.getMessage();
        } /* else if (ex instanceof ResponseStatusException) {
            status = ((ResponseStatusException) ex).getStatus();
            message = ex.getMessage();
        } */else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = "An unexpected error occurred.";
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(status.getReasonPhrase())
                .status(status)
                .message(message)
                .path(exchange.getRequest().getPath().value())
                .build();

        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(APPLICATION_JSON);

        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(errorResponse.toString().getBytes())));
    }
}
