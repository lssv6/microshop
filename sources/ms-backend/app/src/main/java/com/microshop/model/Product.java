package com.microshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import lombok.Data;

@Entity
@Table(name = "product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    private Long id;

    @Column private String name;

    @Column(length = 50_000)
    private String description;

    @Column(length = 50_000)
    private String tagDescription;

    @Column private String friendlyName;

    @Column private Long price;
    @Column private Long oldPrice;

    // @MapsId means that the id of the imageData is the same as this product.
    // @OneToOne(optional = true)
    // private ImageData imageData;

    @ManyToOne(optional = false)
    private Seller seller;

    @ManyToOne(optional = false)
    private Category category;

    @ManyToOne(optional = false)
    private Manufacturer manufacturer;

    // @Version declares the field as version number, used in order to manage versions of the row.
    // It's used to detect optimistic lock failures.
    @Version @Column private Long version;
}
