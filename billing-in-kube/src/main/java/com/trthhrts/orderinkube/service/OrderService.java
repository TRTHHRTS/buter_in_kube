package com.trthhrts.orderinkube.service;

import com.trthhrts.orderinkube.dto.OrderInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;

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

    private static String getBearerTokenHeader() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
    }
}
