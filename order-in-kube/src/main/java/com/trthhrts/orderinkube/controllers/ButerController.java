package com.trthhrts.orderinkube.controllers;

import com.trthhrts.orderinkube.model.Buter;
import com.trthhrts.orderinkube.repository.ButerRepository;
import com.trthhrts.orderinkube.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/buter")
@RequiredArgsConstructor
public class ButerController {

   /** Сервис по работе с пользователями */
   private final ButerRepository buterRepository;

   @GetMapping
   public ResponseEntity<List<Buter>> getButers() {
      return ResponseEntity.ok(buterRepository.findAll());
   }


}
