package com.trthhrts.orderinkube.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "positions")
@Getter
@Setter
@NoArgsConstructor
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name="order_id", nullable=false)
    @JsonIgnore
    private Order order;

    @Column(name = "buter_id", nullable=false)
    private Long buterId;

    @Column(nullable=false)
    private int quantity;

    public Position(Order order, Long buterId, int quantity) {
        this.order = order;
        this.buterId = buterId;
        this.quantity = quantity;
    }
}
