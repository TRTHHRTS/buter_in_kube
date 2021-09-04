package com.trthhrts.orderinkube.service;

import com.trthhrts.orderinkube.service.remote.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BillingService {

    private final AuthService authService;

    public void deposit(Long amount) {
        authService.deposit(amount);
    }

    public void withdraw(Long amount) {
        authService.withdraw(amount);
    }
}
