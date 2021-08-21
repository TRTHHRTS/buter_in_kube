package org.trthhrts.rest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginDto {

   private String username;
   private String password;
   private Boolean rememberMe;
   private String email;
   private Long balance;


}
