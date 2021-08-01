package org.trthhrts.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trthhrts.model.User;
import org.trthhrts.repository.UserRepository;
import org.trthhrts.security.NoSuchUserException;
import org.trthhrts.security.SecurityUtils;

@Service
@Transactional
public class UserService {

   private final UserRepository userRepository;

   public UserService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   @Transactional(readOnly = true)
   public User getUserWithAuthorities() {
      return SecurityUtils.getCurrentUsername()
              .flatMap(userRepository::findOneWithAuthoritiesByUsername)
              .orElseThrow(() -> new NoSuchUserException("Такого пользователя не существует"));
   }

}
