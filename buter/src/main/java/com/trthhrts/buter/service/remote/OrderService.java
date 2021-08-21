package com.trthhrts.buter.service.remote;

import com.trthhrts.buter.dto.Buter;
import com.trthhrts.buter.dto.OrderInfo;
import com.trthhrts.buter.dto.PositionsInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static com.trthhrts.buter.service.remote.RemoteServiceUtils.getBearerTokenHeader;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final WebClient webClient;

    @Value("${services.order-service.url}")
    private String orderServiceUri;

    public OrderInfo getOrderInfo(Long orderId, Long userId) {
        String token = getBearerTokenHeader();
        return webClient.get()
                .uri( orderServiceUri + "/api/order/id")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(OrderInfo.class)
                .block();
    }

    public Buter[] getButers() {
        return webClient.get()
                .uri( orderServiceUri + "/api/buter")
                .retrieve()
                .bodyToMono(Buter[].class)
                .block();
    }

    public OrderInfo[] getOrders() {
        String token = getBearerTokenHeader();
        return webClient.get()
                .uri( orderServiceUri + "/api/order")
                .header("Authorization", token)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> Mono.error(new RuntimeException("Некорректный токен")))
                .bodyToMono(OrderInfo[].class)
                .block();
    }

    public Long createOrder(List<PositionsInfo> positionsInfos) {
        String token = getBearerTokenHeader();
        return webClient.post()
                .uri( orderServiceUri + "/api/order")
                .header("Authorization", token)
                .body(Mono.just(positionsInfos), ArrayList.class)
                .retrieve()
                .bodyToMono(Long.class)
                .block();
    }

    public void doneOrder(Long id) {
        String token = getBearerTokenHeader();
        webClient.put()
                .uri( orderServiceUri + "/api/order/" + id + "/done")
                .header("Authorization", token)
                .retrieve()
                .toBodilessEntity().block();
    }
}
