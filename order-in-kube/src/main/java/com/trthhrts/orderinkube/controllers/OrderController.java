package com.trthhrts.orderinkube.controllers;

import com.trthhrts.orderinkube.controllers.dto.OrderStatus;
import com.trthhrts.orderinkube.controllers.dto.PositionsInfo;
import com.trthhrts.orderinkube.model.Buter;
import com.trthhrts.orderinkube.model.Order;
import com.trthhrts.orderinkube.model.Position;
import com.trthhrts.orderinkube.repository.ButerRepository;
import com.trthhrts.orderinkube.repository.OrderRepository;
import com.trthhrts.orderinkube.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

   private final AuthService authService;
   private final ButerRepository buterRepository;

   /** Сервис по работе с пользователями */
   private final OrderRepository orderRepository;

   @GetMapping
   public ResponseEntity<List<Order>> getOrders() {
      Long userId = authService.getUserId();
      return ResponseEntity.ok(orderRepository.findAllByUserId(userId));
   }

   @GetMapping("/{id}/positions")
   public ResponseEntity<List<Position>> getOrderPositions(@PathVariable Long id) {
      Long userId = authService.getUserId();
      Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Заказ с id=" + id + " не найден."));
      if (!Objects.equals(order.getUserId(), userId)) {
         return ResponseEntity.badRequest().build();
      }
      return ResponseEntity.ok(order.getPositions());
   }

   @PostMapping
   public ResponseEntity<Long> newOrder(@RequestBody List<PositionsInfo> posInfos) {
      Long userId = authService.getUserId();
      Order order = new Order();
      order.setUserId(userId);
      order.setOrderDate(LocalDateTime.now());
      List<Position> positions = new ArrayList<>();
      for (PositionsInfo info : posInfos) {
         Buter buter = buterRepository.findById(info.getButerId())
                 .orElseThrow(() -> new RuntimeException("Бутер с id=" + info.getButerId() + " не найден."));
         if (buter.getQuantity() < info.getQuantity()) {
            throw new RuntimeException("Бутеров с id=" + info.getButerId() + " недостаточно.");
         }
         positions.add(new Position(buter.getId(), info.getQuantity()));
      }
      order.setPositions(positions);
      order.setStatus(OrderStatus.NEW.name());
      orderRepository.save(order);
      return ResponseEntity.ok(order.getId());
   }

   @ExceptionHandler(value = BadCredentialsException.class)
   public ResponseEntity handleBlogAlreadyExistsException(BadCredentialsException badCredentialsException) {
      return new ResponseEntity(badCredentialsException.getMessage(), HttpStatus.UNAUTHORIZED);
   }
}
