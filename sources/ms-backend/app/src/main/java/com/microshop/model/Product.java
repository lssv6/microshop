package com.microshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(value = AccessLevel.NONE)
    private Long id;

    @Column(unique = true)
    private String name;

    private String friendlyName;

    @Lob // Lob means Large OBject. It's just a varchar without size restrictions.
    private String description;

    @Lob
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
