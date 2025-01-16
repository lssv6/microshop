package com.microshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long code;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(unique = true)
    private String friendlyName;

    private String description;
    private String tagDescription;

    @ManyToOne(optional = true)
    @JoinColumn(name = "sellerId")
    private Seller seller;

    @ManyToOne(optional = true)
    @JoinColumn(name = "categoryId")
    private Category category;

    @Column(nullable = false)
    private Double price;

    private Double oldPrice;

    private String warranty;
}
