package org.trthhrts.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.trthhrts.security.model.User;
import org.trthhrts.security.repository.UserRepository;
import org.trthhrts.security.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PersonRestController {

   /** Сервис по работе с пользователями */
   private final UserService userService;

   /** Источник данных по пользователям */
   private final UserRepository userRepository;

   @GetMapping("/person")
   public ResponseEntity<User> getPerson() {
      Optional<User> user = userService.getUserWithAuthorities();
      if (user.isPresent()) {
         return ResponseEntity.ok(user.get());
      }
      throw new UsernameNotFoundException("Cannot get current username");
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
}
