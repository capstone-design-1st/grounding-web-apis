package org.example.first.groundingwebapis.repository;

import org.example.first.groundingwebapis.entity.Order;
import org.example.first.groundingwebapis.entity.OrderPiece;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderPieceRepository extends JpaRepository<OrderPiece, Long> {

}
