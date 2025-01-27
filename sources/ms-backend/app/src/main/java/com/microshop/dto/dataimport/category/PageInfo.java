package com.microshop.dto.dataimport.category;

import lombok.Data;

@Data
public class PageInfo {
    private String cursor;
    private int number;
    private int size;
    private boolean isCurrentPage;
}
