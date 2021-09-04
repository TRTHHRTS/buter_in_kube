package com.trthhrts.orderinkube.service.remote;

import com.trthhrts.orderinkube.dto.OrderInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import static com.trthhrts.orderinkube.service.remote.RemoteServiceUtils.getBearerTokenHeader;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final WebClient webClient;

    @Value("${services.order-service.url}")
    private String orderServiceUri;


    public OrderInfo reserveButers(Long orderId) {
        String token = getBearerTokenHeader();
        return WebClient.create(orderServiceUri).put()
                .uri( "/api/order/" + orderId + "/reserve")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(OrderInfo.class)
                .block();
    }

    public OrderInfo freeButers(Long orderId) {
        String token = getBearerTokenHeader();
        return WebClient.create(orderServiceUri).put()
                .uri( "/api/order/" + orderId + "/free")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(OrderInfo.class)
                .block();
    }

    public OrderInfo paidOrder(Long orderId) {
        String token = getBearerTokenHeader();
        return WebClient.create(orderServiceUri).put()
                .uri( "/api/order/" + orderId + "/paid")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(OrderInfo.class)
                .block();
    }

    public OrderInfo rejectOrder(Long orderId) {
        String token = getBearerTokenHeader();
        return WebClient.create(orderServiceUri).put()
                .uri( "/api/order/" + orderId + "/reject")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(OrderInfo.class)
                .block();
    }

    public OrderInfo getOrderInfo(Long orderId) {
        String token = getBearerTokenHeader();
        return webClient.get()
                .uri( orderServiceUri + "/api/order/" + orderId)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(OrderInfo.class)
                .block();
    }
}
