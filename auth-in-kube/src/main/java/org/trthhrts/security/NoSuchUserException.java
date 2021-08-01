package org.trthhrts.security;

import org.springframework.security.core.AuthenticationException;

public class NoSuchUserException extends AuthenticationException {

   private static final long serialVersionUID = -1122699567574529145L;

   public NoSuchUserException(String message) {
      super(message);
   }
}
