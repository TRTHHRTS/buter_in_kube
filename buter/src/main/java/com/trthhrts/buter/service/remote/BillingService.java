package com.trthhrts.buter.service.remote;

import com.trthhrts.buter.dto.OrderInfo;
import com.trthhrts.buter.dto.PositionsInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static com.trthhrts.buter.service.remote.RemoteServiceUtils.getBearerTokenHeader;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final WebClient webClient;

    @Value("${services.billing-service.url}")
    private String billingServiceUri;

    public String payOrder(@PathVariable Long orderId) {
        String token = getBearerTokenHeader();
        return webClient.post()
                .uri( billingServiceUri + "/api/billing/payOrder/" + orderId)
                .header("Authorization", token)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> Mono.error(new RuntimeException("Некорректный токен")))
                .bodyToMono(String.class)
                .block();
    }
}
