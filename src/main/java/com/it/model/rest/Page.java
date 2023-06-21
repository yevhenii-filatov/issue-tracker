package com.it.model.rest;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

/**
 * @author Yevhenii Filatov
 * @since 3/28/23
 */

@Getter
@Setter
public final class Page<D> {
    private static final int MINUS_ONE = BigDecimal.ONE.negate().intValue();
    private final Collection<D> elements;
    private final int pageSize;
    private final long totalElements;

    private Page() {
        this(MINUS_ONE, null, MINUS_ONE);
    }

    private Page(int pageSize, Collection<D> elements, long totalElements) {
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.elements = elements;
    }

    public int getTotalPages() {
        return pageSize == 0 ? 1 : (int) Math.ceil((double) totalElements / (double) pageSize);
    }

    public <T> Page<T> mapContent(@NotNull Function<D, T> mapper) {
        List<T> mappedElements = this.elements == null ? null : this.elements.stream().map(mapper).toList();
        return new Page<>(this.pageSize, mappedElements, this.totalElements);
    }

    public static <D> Page<D> empty() {
        return new Page<>(0, Collections.emptyList(), 0);
    }

    public static final class PageBuilder<D> {
        private List<D> elements;
        private int pageSize = MINUS_ONE;
        private long totalElements;

        public PageBuilder<D> addElement(D element) {
            if (this.elements == null) {
                this.elements = new ArrayList<>();
            }
            this.elements.add(element);
            return this;
        }

        public PageBuilder<D> elements(List<D> elements) {
            this.elements = elements;
            return this;
        }

        public PageBuilder<D> totalElements(long totalElements) {
            this.totalElements = totalElements;
            return this;
        }

        public PageBuilder<D> pageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Page<D> build() {
            return new Page<>(pageSize, elements, totalElements);
        }

        public Page<D> sortAndBuild(Comparator<D> comparable) {
            elements.sort(comparable);
            return new Page<>(pageSize, elements, totalElements);
        }
    }
}
