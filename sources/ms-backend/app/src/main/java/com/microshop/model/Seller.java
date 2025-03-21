package com.microshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.Data;

@Entity
@Data
public class Seller{
    @Id
    private Long id;
    @Column
    private String name;

    @Version
    @Column
    private Long version;
}
