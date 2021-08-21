package org.trthhrts.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "authority")
@Getter
@Setter
public class Authority {

   @Id
   @Column(name = "name", length = 50)
   private String name;
}
