package org.example.first.groundingwebapis.repository;

import org.example.first.groundingwebapis.entity.InvestmentStatus;
import org.example.first.groundingwebapis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
}
