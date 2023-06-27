package com.it.controller;

import com.it.model.domain.Project;
import com.it.model.rest.ApiResponseDomain;
import com.it.model.rest.ApiResponsePage;
import com.it.model.rest.SingleAttributeUpdate;
import com.it.model.rest.query.ProjectQuery;
import com.it.service.ProjectService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yevhenii Filatov
 * @since 6/21/23
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(ProjectController.PROJECTS_MAPPING)
public class ProjectController {
    public static final String PROJECTS_MAPPING = "/projects";
    private static final String PROJECT_ID_MAPPING = "{/projectId}";
    private static final String NAME_MAPPING = PROJECT_ID_MAPPING + "/name";
    private static final String DESCRIPTION_MAPPING = PROJECT_ID_MAPPING + "/description";
    private static final String ADD_USER_MAPPING = PROJECT_ID_MAPPING + "/add-user";
    private static final String DELETE_USER_MAPPING = PROJECT_ID_MAPPING + "/delete-user";

    private final ProjectService projectService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ApiResponseDomain<Project> create(@NotNull @Valid @RequestBody Project project) {
        return ApiResponseDomain.of(projectService.create(project));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ApiResponsePage<Project> find(@NotNull @Valid @RequestParam("q") ProjectQuery query) {
        return ApiResponsePage.of(projectService.find(query));
    }

    @GetMapping(PROJECT_ID_MAPPING)
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ApiResponseDomain<Project> findById(@NotNull @Positive @PathVariable Long projectId) {
        return ApiResponseDomain.of(projectService.findById(projectId));
    }

    @DeleteMapping(PROJECT_ID_MAPPING)
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ApiResponseDomain<Project> deleteById(@NotNull @Positive @PathVariable Long projectId) {
        return ApiResponseDomain.of(projectService.deleteById(projectId));
    }

    @PatchMapping(NAME_MAPPING)
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ApiResponseDomain<Project> patchName(@NotNull @Valid @PathVariable Long projectId,
                                                @NotNull @Valid @RequestBody SingleAttributeUpdate<String> update) {
        return ApiResponseDomain.of(projectService.patchName(projectId, update.getValue()));
    }

    @PatchMapping(DESCRIPTION_MAPPING)
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ApiResponseDomain<Project> patchDescription(@NotNull @Valid @PathVariable Long projectId,
                                                       @NotNull @Valid @RequestBody SingleAttributeUpdate<String> update) {
        return ApiResponseDomain.of(projectService.patchDescription(projectId, update.getValue()));
    }

    @PatchMapping(ADD_USER_MAPPING)
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ApiResponseDomain<Project> addUser(@NotNull @Valid @PathVariable Long projectId,
                                              @NotNull @Valid @RequestParam("userId") Long userId) {
        return ApiResponseDomain.of(projectService.addUser(projectId, userId));
    }

    @PatchMapping(DELETE_USER_MAPPING)
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ApiResponseDomain<Project> deleteUser(@NotNull @Valid @PathVariable Long projectId,
                                                 @NotNull @Valid @RequestParam("userId") Long userId) {
        return ApiResponseDomain.of(projectService.deleteUser(projectId, userId));
    }
}
