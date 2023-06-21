package com.it.controller;

import com.it.model.common.TaskPriority;
import com.it.model.common.TaskStatus;
import com.it.model.common.TaskType;
import com.it.model.domain.Task;
import com.it.model.rest.ApiResponseDomain;
import com.it.model.rest.ApiResponsePage;
import com.it.model.rest.SingleAttributeUpdate;
import com.it.model.rest.query.TaskQuery;
import com.it.service.TaskService;
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
@RequestMapping(TaskController.TASKS_MAPPING)
public class TaskController {
    public static final String TASKS_MAPPING = "/tasks";
    private static final String TASK_ID_MAPPING = "/{taskId}";
    private static final String TITLE_MAPPING = TASK_ID_MAPPING + "/title";
    private static final String DESCRIPTION_MAPPING = TASK_ID_MAPPING + "/description";
    private static final String STATUS_MAPPING = TASK_ID_MAPPING + "/status";
    private static final String PRIORITY_MAPPING = TASK_ID_MAPPING + "/priority";
    private static final String TYPE_MAPPING = TASK_ID_MAPPING + "/type";
    private static final String ASSIGN_MAPPING = TASK_ID_MAPPING + "/assign";

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ApiResponseDomain<Task> create(@NotNull @Valid @RequestBody Task task) {
        return ApiResponseDomain.of(taskService.create(task));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ApiResponsePage<Task> find(@NotNull @Valid @RequestParam("q") TaskQuery query) {
        return ApiResponsePage.of(taskService.find(query));
    }

    @GetMapping(TASK_ID_MAPPING)
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ApiResponseDomain<Task> findById(@NotNull @Positive @PathVariable Long taskId) {
        return ApiResponseDomain.of(taskService.findById(taskId));
    }

    @DeleteMapping(TASK_ID_MAPPING)
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ApiResponseDomain<Task> deleteById(@NotNull @Positive @PathVariable Long taskId) {
        return ApiResponseDomain.of(taskService.deleteById(taskId));
    }

    @PatchMapping(TITLE_MAPPING)
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ApiResponseDomain<Task> patchTitle(@NotNull @Positive @PathVariable Long taskId,
                                              @NotNull @Valid @RequestBody SingleAttributeUpdate<String> update) {
        return ApiResponseDomain.of(taskService.patchTitle(taskId, update.getValue()));
    }

    @PatchMapping(DESCRIPTION_MAPPING)
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ApiResponseDomain<Task> patchDescription(@NotNull @Positive @PathVariable Long taskId,
                                                    @NotNull @Valid @RequestBody SingleAttributeUpdate<String> update) {
        return ApiResponseDomain.of(taskService.patchDescription(taskId, update.getValue()));
    }

    @PatchMapping(STATUS_MAPPING)
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ApiResponseDomain<Task> patchStatus(@NotNull @Positive @PathVariable Long taskId,
                                               @NotNull @Valid @RequestBody SingleAttributeUpdate<TaskStatus> update) {
        return ApiResponseDomain.of(taskService.patchStatus(taskId, update.getValue()));
    }

    @PatchMapping(PRIORITY_MAPPING)
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ApiResponseDomain<Task> patchPriority(@NotNull @Positive @PathVariable Long taskId,
                                                 @NotNull @Valid @RequestBody SingleAttributeUpdate<TaskPriority> update) {
        return ApiResponseDomain.of(taskService.patchPriority(taskId, update.getValue()));
    }

    @PatchMapping(TYPE_MAPPING)
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ApiResponseDomain<Task> patchType(@NotNull @Positive @PathVariable Long taskId,
                                             @NotNull @Valid @RequestBody SingleAttributeUpdate<TaskType> update) {
        return ApiResponseDomain.of(taskService.patchType(taskId, update.getValue()));
    }

    @PatchMapping(ASSIGN_MAPPING)
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ApiResponseDomain<Task> assignToUser(@NotNull @Positive @PathVariable Long taskId,
                                                @NotNull @Positive @RequestParam("userId") Long userId) {
        return ApiResponseDomain.of(taskService.assignToUser(taskId, userId));
    }
}
