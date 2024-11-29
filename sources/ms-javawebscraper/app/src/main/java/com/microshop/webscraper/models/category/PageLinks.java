package com.microshop.webscraper.models.category;

import lombok.Data;

@Data
public class PageLinks {
    private String redirect;
    private String first;
    private String self;
    private String last;
    private String next;
}
