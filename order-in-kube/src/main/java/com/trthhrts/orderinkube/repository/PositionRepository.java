package com.trthhrts.orderinkube.repository;

import com.trthhrts.orderinkube.model.Position;
import com.trthhrts.orderinkube.model.PositionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, PositionId> {
}
