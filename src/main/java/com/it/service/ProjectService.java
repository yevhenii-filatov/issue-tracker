package com.it.service;

import com.it.model.domain.Project;
import com.it.model.rest.Page;
import com.it.model.rest.query.ProjectQuery;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * @author Yevhenii Filatov
 * @since 6/21/23
 */

public interface ProjectService {
    Project create(@NotNull @Valid Project project);

    Project deleteById(@NotNull @Positive Long projectId);

    Page<Project> find(@NotNull @Valid ProjectQuery query);

    Project findById(@NotNull @Positive Long projectId);

    Project patchName(@NotNull @Positive Long projectId, @NotBlank String name);

    Project patchDescription(@NotNull @Positive Long projectId, @NotBlank String description);

    Project addUser(@NotNull @Positive Long projectId, @NotNull @Positive Long userId);

    Project deleteUser(@NotNull @Positive Long projectId, @NotNull @Positive Long userId);
}
