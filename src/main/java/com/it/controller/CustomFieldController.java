package com.it.controller;

import com.it.model.domain.CustomField;
import com.it.model.rest.ApiResponseDomain;
import com.it.service.CustomFieldService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yevhenii Filatov
 * @since 6/21/23
 */

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(CustomFieldController.CUSTOM_FIELDS_MAPPING)
public class CustomFieldController {
    public static final String CUSTOM_FIELDS_MAPPING = TaskController.TASKS_MAPPING + "/custom-fields";
    private static final String CUSTOM_FIELD_ID_MAPPING = "/{customFieldId}";

    private final CustomFieldService customFieldService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ApiResponseDomain<CustomField> create(@NotNull @Valid @RequestBody CustomField customField) {
        return ApiResponseDomain.of(customFieldService.create(customField));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ApiResponseDomain<CustomField> update(@NotNull @Valid @RequestBody CustomField customField) {
        return ApiResponseDomain.of(customFieldService.update(customField));
    }

    @DeleteMapping(CUSTOM_FIELD_ID_MAPPING)
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ApiResponseDomain<CustomField> deleteById(@NotNull @Positive @PathVariable Long customFieldId) {
        return ApiResponseDomain.of(customFieldService.deleteById(customFieldId));
    }
}
