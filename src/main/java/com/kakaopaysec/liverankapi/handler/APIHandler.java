package com.kakaopaysec.liverankapi.handler;

import com.kakaopaysec.liverankapi.dto.APIResponse;
import com.kakaopaysec.liverankapi.dto.StockRankParamsDTO;
import com.kakaopaysec.liverankapi.domain.entity.StockDetail;
import com.kakaopaysec.liverankapi.service.APIService;
import com.kakaopaysec.liverankapi.validator.StockRankParamsValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class APIHandler {
    private final APIService apiService;

    public APIHandler(APIService apiService) {
        this.apiService = apiService;
    }

    public Mono<ServerResponse> getStockRanking(ServerRequest request) {
        StockRankParamsDTO params = createAndValidateParams(request);

        return apiService.getStockInfos(params)
                .collectList()
                .flatMap(stockInfos -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(APIResponse.builder()
                                .nextOffset(params.getNextOffset() + params.getPageSize())
                                .message("success")
                                .status(HttpStatus.OK)
                                .data(stockInfos)
                                .build()
                        ));
    }

    public Mono<ServerResponse> updateStockDetails(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(apiService.updateStockDetails(), StockDetail.class);
    }

    private StockRankParamsDTO createAndValidateParams(ServerRequest request) {
        int tag = request.queryParam("tag").map(Integer::parseInt).orElse(1);
        int nextOffset = request.queryParam("nextOffset").map(Integer::parseInt).orElse(0);
        int pageSize = request.queryParam("pageSize").map(Integer::parseInt).orElse(10);

        StockRankParamsDTO params = StockRankParamsDTO.builder()
                .nextOffset(nextOffset)
                .pageSize(pageSize)
                .tag(tag)
                .build();

        StockRankParamsValidator.validate(params);

        return params;
    }
}
