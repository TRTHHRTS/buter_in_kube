package org.trthhrts.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trthhrts.security.SecurityUtils;
import org.trthhrts.model.User;
import org.trthhrts.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional
public class UserService {

   private final UserRepository userRepository;

   public UserService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   @Transactional(readOnly = true)
   public Optional<User> getUserWithAuthorities() {
      return SecurityUtils.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
   }

}
