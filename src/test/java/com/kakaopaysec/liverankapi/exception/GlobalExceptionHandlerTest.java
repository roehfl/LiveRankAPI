package com.kakaopaysec.liverankapi.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    public void testIllegalArgumentException() {
        Exception exception = new IllegalArgumentException("Exception thrown");
        MockServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("http://localhost/test").build());
        AtomicReference<HttpStatus> responseStatus = new AtomicReference<>();

        Mono<Void> responseMono = this.globalExceptionHandler.handle(exchange, exception);

        StepVerifier.create(responseMono)
                .expectSubscription()
                .then(() -> responseStatus.set((HttpStatus) exchange.getResponse().getStatusCode()))
                .verifyComplete();

        assertEquals(HttpStatus.BAD_REQUEST, responseStatus.get());
    }

    @Test
    public void testOtherException() {
        Exception exception = new RuntimeException("Exception thrown");
        MockServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("http://localhost/test").build());
        AtomicReference<HttpStatus> responseStatus = new AtomicReference<>();

        Mono<Void> responseMono = this.globalExceptionHandler.handle(exchange, exception);

        StepVerifier.create(responseMono)
                .expectSubscription()
                .then(() -> responseStatus.set((HttpStatus) exchange.getResponse().getStatusCode()))
                .verifyComplete();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseStatus.get());
    }
}
