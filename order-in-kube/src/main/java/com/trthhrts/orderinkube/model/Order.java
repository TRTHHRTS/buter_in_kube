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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Column
    private LocalDateTime paymentDate;

    @Column(nullable = false)
    private Long cost;

    @Column(nullable = false)
    private Long userId;

    @OneToMany(mappedBy="order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Position> positions = new ArrayList<>();
}
