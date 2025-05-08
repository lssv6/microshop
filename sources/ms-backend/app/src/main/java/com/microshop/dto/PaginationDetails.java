package com.microshop.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.BindParam;

import java.util.Optional;

public class PaginationDetails {
    @PositiveOrZero private final Long pageNumber;
    @Positive private final Long pageSize;

    private String sortBy;

    private boolean isDescending = false;

    public PaginationDetails(
            @BindParam("page-number") Optional<Long> pageNumber,
            @BindParam("page-size") Optional<Long> pageSize,
            @BindParam("sort-by") Optional<String> sortBy) {
        this.pageNumber = pageNumber.orElseGet(() -> 0L);
        this.pageSize = pageSize.orElseGet(() -> 20L);
        this.sortBy = sortBy.orElseGet(() -> "");
        if (this.sortBy.startsWith("-")) {
            isDescending = true;
            this.sortBy = this.sortBy.substring(1);
            if (this.sortBy.length() < 1) {
                throw new IllegalArgumentException(
                        "Can't create a sorting property with a empty string.");
            }
        }
    }

    public Pageable getPageable() {
        return PageRequest.of(pageNumber.intValue(), pageSize.intValue())
                .withSort(Sort.by(isDescending ? Direction.DESC : Direction.ASC, sortBy));
    }
}
