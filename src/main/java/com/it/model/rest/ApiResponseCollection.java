package com.it.model.rest;

import com.it.model.domain.AbstractDomainObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * @author Yevhenii Filatov
 * @since 4/7/23
 */

@Getter
@Setter
@AllArgsConstructor
public class ApiResponseCollection<D extends AbstractDomainObject> extends AbstractApiResponse {
    private final Collection<D> domainObjects;

    public Optional<Collection<D>> getDomainObjects() {
        return Optional.ofNullable(this.domainObjects);
    }

    public static <D extends AbstractDomainObject> ApiResponseCollection<D> empty() {
        return new ApiResponseCollection<>(Collections.emptyList());
    }

    public static <D extends AbstractDomainObject> ApiResponseCollection<D> of(Collection<D> domainObjects) {
        return new ApiResponseCollection<>(domainObjects);
    }
}
