package org.trthhrts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trthhrts.model.User;
import org.trthhrts.repository.AuthorityRepository;
import org.trthhrts.repository.UserRepository;
import org.trthhrts.rest.dto.LoginDto;
import org.trthhrts.security.NoSuchUserException;
import org.trthhrts.security.SecurityUtils;

import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

   private final UserRepository userRepository;
   private final AuthorityRepository authorityRepository;

   public User getUserWithAuthorities() {
      return SecurityUtils.getCurrentUsername()
              .flatMap(userRepository::findOneWithAuthoritiesByUsername)
              .orElseThrow(() -> new NoSuchUserException("Такого пользователя не существует"));
   }

   public User createNewUser(LoginDto loginDto) {
      User user = new User();
      user.setUsername(loginDto.getUsername());
      user.setPassword(new BCryptPasswordEncoder().encode(loginDto.getPassword()));
      user.setBalance(loginDto.getBalance());
      user.setEmail(loginDto.getEmail());
      user.setAuthorities(Collections.singleton(authorityRepository.findById("ROLE_USER").get()));
      userRepository.save(user);

      return user;
   }

}
