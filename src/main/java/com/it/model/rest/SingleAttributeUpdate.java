package com.it.model.rest;

import com.it.model.domain.AbstractDomainObject;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Yevhenii Filatov
 * @since 6/14/23
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SingleAttributeUpdate<T> extends AbstractDomainObject {
    @Valid
    @NotNull
    private T value;

    @Nullable
    private String description;
}
