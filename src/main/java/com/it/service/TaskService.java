package com.it.service;

import com.it.model.common.TaskPriority;
import com.it.model.common.TaskStatus;
import com.it.model.common.TaskType;
import com.it.model.domain.Task;
import com.it.model.rest.Page;
import com.it.model.rest.query.TaskQuery;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * @author Yevhenii Filatov
 * @since 6/21/23
 */

public interface TaskService {
    Task create(@NotNull @Valid Task task);

    Page<Task> find(@NotNull @Valid TaskQuery query);

    Task findById(@NotNull @Positive Long taskId);

    Task deleteById(@NotNull @Positive Long taskId);

    Task patchTitle(@NotNull @Positive Long taskId, @NotBlank String title);

    Task patchDescription(@NotNull @Positive Long taskId, @NotBlank String description);

    Task patchStatus(@NotNull @Positive Long taskId, @NotNull TaskStatus status);

    Task patchPriority(@NotNull @Positive Long taskId, @NotNull TaskPriority priority);

    Task patchType(@NotNull @Positive Long taskId, @NotNull TaskType type);

    Task assignToUser(@NotNull @Positive Long taskId, @NotNull @Positive Long userId);
}
