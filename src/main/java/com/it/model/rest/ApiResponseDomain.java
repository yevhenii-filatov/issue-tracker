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
public class ApiResponseDomain<D extends AbstractDomainObject> extends AbstractApiResponse {
    private final D domainObject;

    public Optional<D> getDomainObject() {
        return Optional.ofNullable(this.domainObject);
    }

    public static <D extends AbstractDomainObject> ApiResponseDomain<D> of(D domainObject) {
        return new ApiResponseDomain<>(domainObject);
    }
}
