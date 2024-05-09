package com.grounding.api.repository;

import com.grounding.api.entity.InvestmentStatus;
import com.grounding.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
}
