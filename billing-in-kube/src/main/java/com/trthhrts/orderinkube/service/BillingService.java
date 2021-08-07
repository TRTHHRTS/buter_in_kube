package com.trthhrts.orderinkube.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BillingService {


    public void deposit(Long amount) {

    }

    public void withdraw(Long userId, Long amount) {
        log.info("Снятие средств со счета пользователя (ID={}, amount={})", userId, amount);
        // TODO
    }
}
