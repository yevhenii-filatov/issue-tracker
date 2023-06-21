package com.it.model.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Yevhenii Filatov
 * @since 3/28/23
 */

@Getter
@Setter
public class Pageable {
    public static final int MIN_PAGE_NUMBER = 0;
    public static final int MIN_PAGE_SIZE = 1;
    public static final int MAX_PAGE_SIZE = 500;

    @NotNull
    @Min(MIN_PAGE_SIZE)
    @Max(MAX_PAGE_SIZE)
    private Integer pageSize;

    @NotNull
    @Min(MIN_PAGE_NUMBER)
    private Integer pageNumber;

    @Valid
    private Collection<Sort> sorts;

    private String sortPrefix;

    public static Pageable withMaxElements() {
        return of(MIN_PAGE_NUMBER, MAX_PAGE_SIZE);
    }

    public static Pageable of(int pageNumber, int pageSize) {
        Pageable pageable = new Pageable();
        pageable.setPageNumber(Math.max(pageNumber, MIN_PAGE_NUMBER));
        pageable.setPageSize(pageSize < MAX_PAGE_SIZE ? Math.max(MIN_PAGE_SIZE, pageSize) : MAX_PAGE_SIZE);
        return pageable;
    }

    public long skipCount() {
        return (long) getPageSize() * getPageNumber();
    }

    public int pageCount(long totalCount) {
        return getPageCount(totalCount, pageSize);
    }

    public Collection<Sort> getPrefixedSorts() {
        return sorts == null || sorts.isEmpty()
           ? null
           : sorts
           .stream()
           .map(sortParameter -> {
               Sort newSortParameter = new Sort();
               newSortParameter.setFieldName(withPrefix(sortParameter.getFieldName()));
               newSortParameter.setSortOrder(sortParameter.getSortOrder());
               return newSortParameter;
           })
           .collect(Collectors.toList());
    }

    private String withPrefix(String fieldName) {
        return sortPrefix == null || "".equals(sortPrefix) ? fieldName : sortPrefix + "." + fieldName;
    }

    public static int getPageCount(long itemCount, int pageSize) {
        int result;
        if (pageSize > 0) {
            long resultPageCount = itemCount / pageSize;
            if (itemCount % pageSize > 0) {
                resultPageCount++;
            }
            result = (int) resultPageCount;
        } else {
            result = -1;
        }
        return result;
    }
}
