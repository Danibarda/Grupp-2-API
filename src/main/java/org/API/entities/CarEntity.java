package org.API.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CarEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String manufacturer;
    private int year;

    private String model;
    private int milage;
    private String imgUrl;
    private String regNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
