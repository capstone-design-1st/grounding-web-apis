package com.grounding.api.repository;

import com.grounding.api.entity.Notification;
import com.grounding.api.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByTypeAndId(@Param("type") String type, @Param("id") Long id);
}
