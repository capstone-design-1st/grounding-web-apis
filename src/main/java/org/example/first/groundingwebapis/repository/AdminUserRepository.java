package org.example.first.groundingwebapis.repository;

import org.example.first.groundingwebapis.entity.AdminUser;
import org.example.first.groundingwebapis.entity.Disclosure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
// 
    AdminUser findByUserName(@Param("userName") String userName);
}