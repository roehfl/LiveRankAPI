package com.kakaopaysec.liverankapi.handler;

import com.kakaopaysec.liverankapi.domain.dto.StockInfoDTO;
import com.kakaopaysec.liverankapi.domain.dto.StockRankParamsDTO;
import com.kakaopaysec.liverankapi.domain.entity.StockDetail;
import com.kakaopaysec.liverankapi.service.APIService;
import org.h2.tools.Server;
import org.springframework.data.domain.Sort;
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
        String tag = request.queryParam("tag").orElseThrow();
        int pageNumber = request.queryParam("pageNumber").map(Integer::parseInt).orElse(0);
        int pageSize = request.queryParam("pageSize").map(Integer::parseInt).orElse(10);
        Sort.Direction sortOrder = Sort.Direction.valueOf(request.queryParam("sortOrder").orElse("DESC"));

        StockRankParamsDTO params = StockRankParamsDTO.builder()
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .tag(tag)
                .sortOrder(sortOrder)
                .build();
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(apiService.getStockInfos(params), StockInfoDTO.class);
    }

    public Mono<ServerResponse> updateStockDetails(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(apiService.updateStockDetails(), StockDetail.class);
    }

}
