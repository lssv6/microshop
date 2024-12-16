package com.microshop.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerDTO {
    @NotNull
    private Long id;

    @NotNull
    @Size(min = 1, message = "A name must have at least 1 character of length")
    private String name;
}
