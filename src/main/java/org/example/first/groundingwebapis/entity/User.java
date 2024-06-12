package org.example.first.groundingwebapis.entity;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.example.first.groundingwebapis.dto.UserDto;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "pw", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 10)
    private Role role;

    @Column(name = "wallet_address")
    private String walletAddress;

    @Column(name = "asset_certificate_url")
    private String assetCertificateUrl;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private boolean withdraw;

    @Column(name = "user_profile_url")
    private String userProfileUrl;

    @Builder
    public User(
            Long userId,
            String email,
            String password,
            String phoneNumber,
            String name,
            Role role,
            String walletAddress) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.role = role;
        this.walletAddress = walletAddress;
    }

    public UserDto toDto() {
        return UserDto.builder()
                .userId(userId)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .name(name)
                .build();
    }

}