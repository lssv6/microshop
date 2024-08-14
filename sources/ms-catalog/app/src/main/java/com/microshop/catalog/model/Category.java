package com.microshop.catalog.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Category implements Serializable{
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Getter@Setter
    @Column(length = 64, unique = true, nullable = false)
    private String name;

    @Getter@Setter
    @Column(nullable = false)
    private boolean hidden = false;

    @Getter@Setter
    @ManyToOne
    private Category parent;

    @Getter@Setter
    @OneToMany(mappedBy = "parent")
    private Set<Category> children;
    
    //@Getter@Setter
    //@OneToMany
    //private Set<Product> products;
}

