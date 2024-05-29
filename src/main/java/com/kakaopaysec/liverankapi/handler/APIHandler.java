package com.kakaopaysec.liverankapi.handler;

import com.kakaopaysec.liverankapi.domain.entity.StockDetail;
import com.kakaopaysec.liverankapi.service.APIService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class APIHandler {
    private final APIService apiService;

    public APIHandler(APIService apiService) {
        this.apiService = apiService;
    }

    public Mono<ServerResponse> getStockRanking(ServerRequest request) {
        Mono<String> slogan = Mono.just("CodeCouple roxx!");
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(slogan, String.class);
    }

    public Mono<ServerResponse> updateStockDetails(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(apiService.updateStockDetails(), StockDetail.class);
    }

}
