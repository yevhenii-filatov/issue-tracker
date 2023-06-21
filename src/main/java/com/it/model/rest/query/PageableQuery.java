package com.it.model.rest.query;

import com.it.model.rest.Pageable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Yevhenii Filatov
 * @since 3/28/23
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PageableQuery extends Query {
    @Valid
    @NotNull
    private Pageable pageable = Pageable.of(0, 10);
}
