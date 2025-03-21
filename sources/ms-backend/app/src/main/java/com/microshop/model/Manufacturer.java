package com.microshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.Data;

@Entity
@Data
public class Manufacturer{
    @Id
    private Long id;
    @Column(unique = true)
    private String name;
    @Column
    private String img;
    @Version
    @Column
    private Long version;
}
