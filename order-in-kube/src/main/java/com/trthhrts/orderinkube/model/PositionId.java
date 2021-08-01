package com.trthhrts.orderinkube.model;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PositionId implements Serializable {

    private String orderId;

    private String buterId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionId that = (PositionId) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(buterId, that.buterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, buterId);
    }
}
