package com.bid90.hpm.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product_tb")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name",nullable = false)
    String name;

    @Column(name = "price",nullable = false)
    double price;

}
