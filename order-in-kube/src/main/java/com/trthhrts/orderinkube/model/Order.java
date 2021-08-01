package com.trthhrts.orderinkube.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String status;

    @Column
    private LocalDateTime orderDate;

    @Column
    private LocalDateTime paymentDate;

    @Column
    private Long cost;

    @Column
    private Long userId;

    @OneToMany
    private List<Position> positions = new ArrayList<>();
}
