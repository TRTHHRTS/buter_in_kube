package com.trthhrts.orderinkube.controllers;

import com.trthhrts.orderinkube.controllers.dto.OrderStatus;
import com.trthhrts.orderinkube.controllers.dto.PositionsInfo;
import com.trthhrts.orderinkube.model.Buter;
import com.trthhrts.orderinkube.model.Order;
import com.trthhrts.orderinkube.model.Position;
import com.trthhrts.orderinkube.repository.ButerRepository;
import com.trthhrts.orderinkube.repository.OrderRepository;
import com.trthhrts.orderinkube.service.remote.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
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
@Slf4j
public class OrderController {

   /** Сервис по работе с пользователями */
   private final AuthService authService;
   private final ButerRepository buterRepository;
   private final OrderRepository orderRepository;

   @GetMapping
   public ResponseEntity<List<Order>> getOrders() {
      log.info("Запрос получения информации о заказах");
      Long userId = authService.getUserId();
      return ResponseEntity.ok(orderRepository.findAllByUserId(userId));
   }

   @GetMapping("/{id}")
   public ResponseEntity<Order> getOrder(@PathVariable Long id) {
      log.info("Запрос получения информации о заказе (id={})", id);
      checkAuth();
      Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Заказ с id=" + id + " не найден."));
      return ResponseEntity.ok(order);
   }

   @PutMapping("/{id}/reserve")
   public ResponseEntity<Order> reserveButers(@PathVariable Long id) {
      log.info("Запрос бронирования бутеров (id заказа={})", id);
      checkAuth();
      Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Заказ с id=" + id + " не найден."));
      for (Position position : order.getPositions()) {
         Buter buter = buterRepository.findById(position.getButerId()).orElseThrow(() -> new RuntimeException("Бутер с id=" + id + " не найден."));
         if (buter.getQuantity() < position.getQuantity()) {
            throw new RuntimeException("Бутеров с id=" + position.getButerId() + " недостаточно!");
         }
         buter.setQuantity(buter.getQuantity() - position.getQuantity());
         buterRepository.save(buter);
      }
      order.setStatus(OrderStatus.RESERVED.name());
      orderRepository.save(order);
      log.info("Бутеры забронированы (id заказа={})", id);
      return ResponseEntity.ok(order);
   }

   @PutMapping("/{id}/paid")
   public ResponseEntity<Order> paidOrder(@PathVariable Long id) {
      log.info("Запрос подтверждения оплаты заказа (id заказа={})", id);
      checkAuth();
      Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Заказ с id=" + id + " не найден."));
      order.setStatus(OrderStatus.PAID.name());
      orderRepository.save(order);
      return ResponseEntity.ok(order);
   }

   @PutMapping("/{id}/done")
   public ResponseEntity<Order> doneOrder(@PathVariable Long id) {
      log.info("Запрос завершения заказа (id заказа={})", id);
      checkAuth();
      Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Заказ с id=" + id + " не найден."));
      order.setStatus(OrderStatus.DONE.name());
      orderRepository.save(order);
      return ResponseEntity.ok(order);
   }

   @GetMapping("/{id}/positions")
   public ResponseEntity<List<Position>> getOrderPositions(@PathVariable Long id) {
      log.info("Запрос получения информации о позициях в заказе (id заказа={})", id);
      Long userId = authService.getUserId();
      Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Заказ с id=" + id + " не найден."));
      if (!Objects.equals(order.getUserId(), userId)) {
         return ResponseEntity.badRequest().build();
      }
      return ResponseEntity.ok(order.getPositions());
   }

   @PostMapping
   public ResponseEntity<Long> newOrder(@RequestBody List<PositionsInfo> posInfos) {
      log.info("Запрос создания нового заказа ({})", JSONArray.toJSONString(posInfos));
      Long userId = authService.getUserId();
      Order order = new Order();
      order.setUserId(userId);
      order.setOrderDate(LocalDateTime.now());

      long cost = 0;
      List<Position> positions = new ArrayList<>();
      for (PositionsInfo info : posInfos) {
         Buter buter = buterRepository.findById(info.getButerId())
                 .orElseThrow(() -> new RuntimeException("Бутер с id=" + info.getButerId() + " не найден."));
         if (buter.getQuantity() < info.getQuantity()) {
            throw new RuntimeException("Бутеров с id=" + info.getButerId() + " недостаточно.");
         }
         cost += buter.getPrice() * info.getQuantity();
         positions.add(new Position(order, buter.getId(), info.getQuantity()));
      }
      order.setPositions(positions);
      order.setStatus(OrderStatus.NEW.name());
      order.setCost(cost);
      orderRepository.save(order);

      return ResponseEntity.ok(order.getId());
   }

   @ExceptionHandler(value = BadCredentialsException.class)
   public ResponseEntity handleBlogAlreadyExistsException(BadCredentialsException badCredentialsException) {
      return new ResponseEntity(badCredentialsException.getMessage(), HttpStatus.UNAUTHORIZED);
   }

   private void checkAuth() {
      authService.getUserId();
   }
}
