package com.microshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class CategorySEO {
    @Id
    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String title;
    private String description;
    private String titleHeading;
}
