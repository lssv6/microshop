package com.microshop.dto.request;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class NewManufacturer {
    @NotBlank(message = "Manufacturer name must not be blank")
    private String name;

    @NotBlank(message = "Manufacturer img must not be blank")
    private String img;
}
