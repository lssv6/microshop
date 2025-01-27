package com.microshop.dto.dataimport.category;

import lombok.Data;

@Data
public class Pagination {
    private int prev;
    private int current;
    private int next;
    private int total;
}
