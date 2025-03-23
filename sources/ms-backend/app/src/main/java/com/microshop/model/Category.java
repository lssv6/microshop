package com.microshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;

import lombok.Data;

@Entity
@Data
public class Category {
    @Id private Long id;

    @Column private String name;

    @Column private String fullName;
    @Column private String path;
    @Column private String fullPath;

    @ManyToOne private Category parent;

    @Version @Column private Long version;
}
