package org.trthhrts.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.trthhrts.model.User;
import org.trthhrts.repository.UserRepository;
import org.trthhrts.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserRestController {

   /** Сервис по работе с пользователями */
   private final UserService userService;
   /** Источник данных по пользователям */
   private final UserRepository userRepository;

   @GetMapping
   public ResponseEntity<User> getActualUser() {
      return ResponseEntity.ok(userService.getUserWithAuthorities().get());
   }

   @PutMapping("/balance/{amount}")
   public void depositBalance(@PathVariable String amount) {
      Long amountLong = Long.parseLong(amount);
      if (amountLong <= 0) {
         throw new ArithmeticException("Пополнить можно только положительной суммой");
      }
      userService.getUserWithAuthorities().ifPresent(user -> {
         user.setBalance(user.getBalance() + amountLong);
         userRepository.save(user);
      });
   }

   @GetMapping("/id")
   public ResponseEntity<Long> getId() {
      Optional<User> user = userService.getUserWithAuthorities();
      return user.map(value -> ResponseEntity.ok(value.getId())).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
   }
}
