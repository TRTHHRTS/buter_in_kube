package com.trthhrts.orderinkube.controllers;

import com.trthhrts.orderinkube.dto.OrderInfo;
import com.trthhrts.orderinkube.service.BillingService;
import com.trthhrts.orderinkube.service.remote.AuthService;
import com.trthhrts.orderinkube.service.remote.OrderService;
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
   public ResponseEntity<String> payOrder(@PathVariable Long orderId) {
      Long userId = authService.getUserId();
      OrderInfo orderInfo = orderService.getOrderInfo(orderId);
      try {
         log.info("Бронирование бутеров (ID заказа={}, userId={})", orderId, userId);
         orderService.reserveButers(orderId);
      } catch (Exception e) {
         log.info("Ошибка при бронирование бутеров, заказ будет отменен (ID заказа={}, userId={})", orderId, userId);
         orderService.rejectOrder(orderId);
         throw e;
      }
      try {
         log.info("Оплата заказа со счета пользователя (ID={}, amount={})", userId, orderInfo.getCost());
         billingService.withdraw(orderInfo.getCost());
      } catch (Exception e) {
         log.info("Ошибка при оплате заказа, бронирование бутеров будет отменено (ID заказа={}, userId={})", orderId, userId);
         orderService.freeButers(orderId);
         log.info("Отмена заказа (ID заказа={}, userId={})", orderId, userId);
         orderService.rejectOrder(orderId);
         throw e;
      }
      orderService.paidOrder(orderId);
      return ResponseEntity.ok("OK");
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
