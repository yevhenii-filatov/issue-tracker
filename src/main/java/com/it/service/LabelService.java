package com.it.service;

import com.it.model.domain.Label;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * @author Yevhenii Filatov
 * @since 6/21/23
 */

public interface LabelService {
    Label create(@NotNull @Valid Label label);

    Label update(@NotNull @Valid Label label);

    Label deleteById(@NotNull @Positive Long labelId);
}
