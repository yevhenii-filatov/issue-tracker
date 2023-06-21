package com.it.service;

import com.it.model.domain.CustomField;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * @author Yevhenii Filatov
 * @since 6/21/23
 */

public interface CustomFieldService {
    CustomField create(@NotNull @Valid CustomField customField);

    CustomField update(@NotNull @Valid CustomField customField);

    CustomField deleteById(@NotNull @Positive Long customFieldId);
}
