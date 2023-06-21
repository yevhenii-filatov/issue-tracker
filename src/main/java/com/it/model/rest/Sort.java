package com.it.model.rest;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Yevhenii Filatov
 * @since 3/28/23
 */

@Setter
@Getter
public class Sort {
    public static final String PATTERN = "ASC|DESC";
    public static final String DEFAULT_SORT_ORDER = "ASC";

    @NotNull
    private String fieldName;

    @Pattern(regexp = PATTERN)
    private String sortOrder = DEFAULT_SORT_ORDER;

    public static Sort of(String fieldName) {
        Sort sort = new Sort();
        sort.fieldName = fieldName;
        return sort;
    }

    public static Sort of(String fieldName, @Pattern(regexp = PATTERN) String sortOrder) {
        Sort sort = new Sort();
        sort.fieldName = fieldName;
        sort.sortOrder = sortOrder;
        return sort;
    }
}

