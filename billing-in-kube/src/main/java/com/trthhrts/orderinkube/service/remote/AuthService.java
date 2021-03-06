package com.trthhrts.orderinkube.service.remote;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.trthhrts.orderinkube.service.remote.RemoteServiceUtils.getBearerTokenHeader;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final WebClient webClient;

    @Value("${services.auth-service.url}")
    private String authUri;

    public Long getUserId() {
        String token = getBearerTokenHeader();

        return webClient.get()
                .uri( authUri + "/api/user/id")
                .header("Authorization", token)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> Mono.error(new RuntimeException("Некорректный токен")))
                .bodyToMono(Long.class)
                .block();
    }

    public Long getUserBalance() {
        String token = getBearerTokenHeader();
        return webClient.get()
                .uri( authUri + "/api/user/balance")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(Long.class)
                .block();
    }

    public String deposit(long amount) {
        String token = getBearerTokenHeader();
        return webClient.put()
                .uri( authUri + "/api/user/balance/deposit/" + amount)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String withdraw(long amount) {
        String token = getBearerTokenHeader();
        return webClient.put()
                .uri( authUri + "/api/user/balance/withdraw/" + amount)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}