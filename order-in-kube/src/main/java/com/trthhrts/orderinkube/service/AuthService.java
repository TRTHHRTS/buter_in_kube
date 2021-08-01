package com.trthhrts.orderinkube.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AuthService {

    private final WebClient client;

    @Value("services.auth-service.url")
    private String authUri;

    AuthService() {
        this.client = WebClient.create(authUri);
    }

    public Long getUserId() {
        String token = getBearerTokenHeader();
        return client.get()
                .uri( "/api/user/id")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(Long.class)
                .block();
    }

    private static String getBearerTokenHeader() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
    }
}
