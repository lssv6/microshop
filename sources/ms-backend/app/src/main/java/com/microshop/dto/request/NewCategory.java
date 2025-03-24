package com.microshop.dto.request;

import lombok.Data;

@Data
public class NewCategory {
    private String name;
    private String path;
    private Long parentId;
}
