package com.trthhrts.orderinkube.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AuthService {

    @Value("${services.auth-service.url}")
    private String authUri;

    public Long getUserId() {
        String token = getBearerTokenHeader();
        return WebClient.create(authUri).get()
                .uri( "/api/user/id")
                .header("Authorization", token)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> Mono.error(new BadCredentialsException("Некорректный токен")))
                .bodyToMono(Long.class)
                .block();
    }

    public Long getUserBalance() {
        String token = getBearerTokenHeader();
        return WebClient.create(authUri).get()
                .uri( "/api/user/balance")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(Long.class)
                .block();
    }

    private static String getBearerTokenHeader() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
    }
}
