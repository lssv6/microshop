package com.microshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import lombok.Data;

@Entity
@Data
public class Product{
    @Id
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String tagDescription;
    @Column
    private String friendlyName;
    @Column
    private Integer price;
    @Column
    private Integer oldPrice;

    // @MapsId means that the id of the imageData is the same as this product.
    //@OneToOne(optional = true)
    //private ImageData imageData;

    @ManyToOne(optional = false)
    private Seller seller;

    @ManyToOne(optional = false)
    private Category category;

    @ManyToOne(optional = false)
    private Manufacturer manufacturer;

    // @Version declares the field as version number, used in order to manage versions of the row.
    // It's used to detect optimistic lock failures.
    @Version
    @Column
    private Long version;
}
