package com.it.model.rest;

import com.it.model.domain.AbstractDomainObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

/**
 * @author Yevhenii Filatov
 * @since 4/7/23
 */

@Getter
@Setter
@AllArgsConstructor
public class ApiResponsePage<D extends AbstractDomainObject> extends AbstractApiResponse {
    private final Page<D> page;

    public Optional<Page<D>> getPage() {
        return Optional.ofNullable(this.page);
    }

    public static <D extends AbstractDomainObject> ApiResponsePage<D> of(Page<D> page) {
        return new ApiResponsePage<>(page);
    }

    public static <D extends AbstractDomainObject> ApiResponsePage<D> empty() {
        return new ApiResponsePage<>(Page.empty());
    }
}
