package com.microshop.api.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Entity
public class Product implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Getter@Setter
    @Column(nullable = false, length = 64, unique = true)
    private String code;
    
    @Getter@Setter
    @Column(nullable = false)
    private String name;
    
    @Getter@Setter
    @Column(nullable = false)  
    private String description;
    
    @Getter@Setter
    @Column(nullable = false)  
    private String technicalInfo;

    @Getter@Setter
    @ManyToOne
    @JoinColumn
    private Category category;
}

