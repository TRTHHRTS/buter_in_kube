package com.trthhrts.orderinkube.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "buters")
@Getter
@Setter
public class Buter {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true)
    private String name;

    @Column
    private String desc;

    @Column
    private int quantity;

    @Column
    private Long price;

}
