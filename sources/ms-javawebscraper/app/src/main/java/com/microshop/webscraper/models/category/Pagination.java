package com.microshop.webscraper.models.category;

import lombok.Data;

@Data
public class Pagination {
    private int prev;
    private int current;
    private int next;
    private int total;
}
