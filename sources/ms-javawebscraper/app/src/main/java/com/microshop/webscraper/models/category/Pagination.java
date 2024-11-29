package com.microshop.webscraper.models.category;

import lombok.Data;

@Data
public class Pagination {
    private int number;
    private int size;
    private String cursor;
    private boolean isCurrentPage;
}
