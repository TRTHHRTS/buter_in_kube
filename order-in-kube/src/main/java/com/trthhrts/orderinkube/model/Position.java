package com.trthhrts.orderinkube.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "positions")
@IdClass(PositionId.class)
@Getter
@Setter
public class Position {

    @Id
    @Column(name = "buter_id")
    private Long buterId;

    @Id
    @Column(name = "order_id")
    private Long orderId;

    @Column
    private int quantity;

    public Position(Long buterId, int quantity) {
        this.buterId = buterId;
        this.quantity = quantity;
    }
}
