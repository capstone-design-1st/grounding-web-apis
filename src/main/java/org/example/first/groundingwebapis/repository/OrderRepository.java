package org.example.first.groundingwebapis.repository;

import org.example.first.groundingwebapis.entity.Notification;
import org.example.first.groundingwebapis.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByTypeAndId(@Param("type") String type, @Param("id") Long id);
}
