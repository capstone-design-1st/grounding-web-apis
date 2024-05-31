package org.example.first.groundingwebapis.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "pw", nullable = false)
    private String password;

    @Column(name = "nickname", length = 10)
    private String nickname;

    @Column(name = "estate_certificate_url")
    private String estateCertificateUrl;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private boolean withdraw;

    @Column(name = "user_profile_url")
    private String userProfileUrl;
}
