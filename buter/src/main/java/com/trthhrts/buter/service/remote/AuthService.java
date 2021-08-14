package com.trthhrts.buter.service.remote;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.trthhrts.buter.service.remote.RemoteServiceUtils.getBearerTokenHeader;

@Service
@RequiredArgsConstructor
public class AuthService {

    final WebClient webClient;

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
}