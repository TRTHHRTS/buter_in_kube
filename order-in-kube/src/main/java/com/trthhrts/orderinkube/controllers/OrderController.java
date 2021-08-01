package com.trthhrts.orderinkube.controllers;

import com.trthhrts.orderinkube.model.Order;
import com.trthhrts.orderinkube.repository.OrderRepository;
import com.trthhrts.orderinkube.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

   private final AuthService authService;

   /** Сервис по работе с пользователями */
   private final OrderRepository orderRepository;

   @GetMapping
   public ResponseEntity<List<Order>> getOrders() {
      Long userId = authService.getUserId();
      return ResponseEntity.ok(orderRepository.findAllByUserId(userId));
   }


}
