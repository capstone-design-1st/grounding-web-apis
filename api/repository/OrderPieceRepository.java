package com.grounding.api.repository;

import com.grounding.api.entity.Order;
import com.grounding.api.entity.OrderPiece;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderPieceRepository extends JpaRepository<OrderPiece, Long> {

}
