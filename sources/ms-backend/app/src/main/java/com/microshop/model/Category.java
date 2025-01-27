package com.microshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Category {
    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = false, nullable = false)
    private String name;

    @Column(unique = false, nullable = false)
    private String path;

    @Column(unique = true, nullable = true)
    private String fullPath;

    @Column(unique = false, nullable = true) // Uniqueness is only setted as false because of importation phase issues.
    private String prettyPath;

    @ManyToOne
    @JoinColumn(nullable = true)
    private Category parentCategory;

    // @Version
    // @Getter(value = AccessLevel.NONE)
    // @Setter(value = AccessLevel.NONE)
    // private Long version;
}
