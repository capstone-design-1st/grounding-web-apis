package org.example.first.groundingwebapis.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city")
    private String city;

    @Column(name = "gu")
    private String gu;

    @Column(name = "dong")
    private String dong;

    @Column(name = "detail")
    private String detail;

    @Column(name = "property")
    private String property;


    public Location() {

    }
}