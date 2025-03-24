package com.microshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;

import lombok.Data;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column private String name;

    @Column private String fullName;
    @Column private String path;
    @Column private String fullPath;

    @ManyToOne(optional = true)
    private Category parent;

    @Version @Column private Long version;
}
