package org.trthhrts.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.trthhrts.model.User;
import org.trthhrts.repository.UserRepository;
import org.trthhrts.service.UserService;

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
      return ResponseEntity.ok(userService.getUserWithAuthorities());
   }

   @GetMapping("/balance")
   public ResponseEntity<Long> getBalance() {
      return ResponseEntity.ok(userService.getUserWithAuthorities().getBalance());
   }

   @PutMapping("/balance/deposit/{amount}")
   public ResponseEntity<String> depositBalance(@PathVariable Long amount) {
      if (amount <= 0) {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Внести можно только положительную сумму");
      }
      User user = userService.getUserWithAuthorities();
      user.setBalance(user.getBalance() + amount);
      userRepository.save(user);
      return ResponseEntity.ok("OK");
   }

   @PutMapping("/balance/withdraw/{amount}")
   public ResponseEntity<String> withdrawBalance(@PathVariable Long amount) {
      if (amount <= 0) {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Снять можно только положительную сумму");
      }
      User user = userService.getUserWithAuthorities();
      if (user.getBalance() - amount < 0) {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Недостаточно средств на счете");
      }
      user.setBalance(user.getBalance() - amount);
      userRepository.save(user);
      return ResponseEntity.ok("OK");
   }

   @GetMapping("/id")
   public ResponseEntity<Long> getId() {
      return ResponseEntity.ok(userService.getUserWithAuthorities().getId());
   }
}
