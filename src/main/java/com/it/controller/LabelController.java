package com.it.controller;

import com.it.model.domain.Label;
import com.it.model.rest.ApiResponseDomain;
import com.it.service.LabelService;
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
@RequestMapping(LabelController.LABELS_MAPPING)
public class LabelController {
    public static final String LABELS_MAPPING = TaskController.TASKS_MAPPING + "/labels";
    private static final String LABEL_ID_MAPPING = "/{labelId}";

    private final LabelService labelService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ApiResponseDomain<Label> create(@NotNull @Valid @RequestBody Label label) {
        return ApiResponseDomain.of(labelService.create(label));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ApiResponseDomain<Label> update(@NotNull @Valid @RequestBody Label label) {
        return ApiResponseDomain.of(labelService.update(label));
    }

    @DeleteMapping(LABEL_ID_MAPPING)
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ApiResponseDomain<Label> deleteById(@NotNull @Positive @PathVariable Long labelId) {
        return ApiResponseDomain.of(labelService.deleteById(labelId));
    }
}
