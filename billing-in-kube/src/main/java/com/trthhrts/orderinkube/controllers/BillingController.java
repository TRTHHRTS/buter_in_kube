package com.trthhrts.orderinkube.controllers;

import com.trthhrts.orderinkube.dto.OrderInfo;
import com.trthhrts.orderinkube.service.AuthService;
import com.trthhrts.orderinkube.service.BillingService;
import com.trthhrts.orderinkube.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/billing")
@RequiredArgsConstructor
@Slf4j
public class BillingController {

   private final AuthService authService;
   private final BillingService billingService;
   private final OrderService orderService;

   @PostMapping("/payOrder/{orderId}")
   public void payOrder(@PathVariable Long orderId) {
      Long userId = authService.getUserId();
      log.info("Оплата заказа (ID={}, userId={})", orderId, userId);
      OrderInfo orderInfo = orderService.getOrderInfo(orderId, userId);
      // TODO
   }

   @PutMapping("/deposit/{amount}")
   public void deposit(@PathVariable Long amount) {
      Long userId = authService.getUserId();
      log.info("Пополнение счета пользователя (ID={}, amount={})", userId, amount);
      billingService.deposit(amount);
   }

   @PutMapping("/withdraw/{amount}")
   public void withdraw(@PathVariable Long amount) {
      Long userId = authService.getUserId();
      log.info("Снятие средств со счета пользователя (ID={}, amount={})", userId, amount);
      billingService.withdraw(amount);
   }

   @GetMapping("/balance")
   public ResponseEntity<Long> getBalance() {
      Long userId = authService.getUserId();
      log.info("Получение баланса для пользователя с ID={}", userId);
      return ResponseEntity.ok(authService.getUserBalance());
   }

   @ExceptionHandler(value = BadCredentialsException.class)
   public ResponseEntity handleBlogAlreadyExistsException(BadCredentialsException badCredentialsException) {
      return new ResponseEntity(badCredentialsException.getMessage(), HttpStatus.UNAUTHORIZED);
   }
}
