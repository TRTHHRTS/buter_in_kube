package com.trthhrts.buter.service.remote;

import com.trthhrts.buter.dto.OrderInfo;
import com.trthhrts.buter.dto.Buter;
import com.trthhrts.buter.dto.PositionsInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static com.trthhrts.buter.service.remote.RemoteServiceUtils.getBearerTokenHeader;

@Service
public class OrderService {

    @Value("${services.order-service.url}")
    private String orderServiceUri;

    public OrderInfo getOrderInfo(Long orderId, Long userId) {
        String token = getBearerTokenHeader();
        return WebClient.create(orderServiceUri).get()
                .uri( "/api/order/id")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(OrderInfo.class)
                .block();
    }

    public List<Buter> getButers() {
        return WebClient.create(orderServiceUri).get()
                .uri( "/api/buter")
                .retrieve()
                .bodyToMono(ArrayList.class)
                .block();
    }

    public String createOrder(List<PositionsInfo> positionsInfos) {
        String token = getBearerTokenHeader();
        return WebClient.create(orderServiceUri).post()
                .uri( "/api/order")
                .header("Authorization", token)
                .body(Mono.just(positionsInfos), ArrayList.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
