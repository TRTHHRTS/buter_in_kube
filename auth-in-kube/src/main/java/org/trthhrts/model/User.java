package org.trthhrts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
public class User {

   @Id
   @Column(nullable = false)
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernateSequence")
   @SequenceGenerator(name = "hibernateSequence")
   private Long id;

   @Column(length = 50, unique = true)
   private String username;

   @JsonIgnore
   @Column(length = 100)
   private String password;

   @Column(length = 50)
   private String email;

   @Column
   private Long balance;

   @ManyToMany
   @JoinTable(
      name = "user_authority",
      joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
   private Set<Authority> authorities = new HashSet<>();

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      User user = (User) o;
      return id.equals(user.id);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id);
   }
}
