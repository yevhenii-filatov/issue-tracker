package com.it.controller;

import com.it.model.domain.Comment;
import com.it.model.rest.ApiResponseDomain;
import com.it.service.CommentService;
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
@RequestMapping(CommentController.COMMENTS_MAPPING)
public class CommentController {
    public static final String COMMENTS_MAPPING = TaskController.TASKS_MAPPING + "/comments";
    private static final String COMMENT_ID_MAPPING = "/{commentId}";

    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ApiResponseDomain<Comment> create(@NotNull @Valid @RequestBody Comment comment) {
        return ApiResponseDomain.of(commentService.create(comment));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ApiResponseDomain<Comment> update(@NotNull @Valid @RequestBody Comment comment) {
        return ApiResponseDomain.of(commentService.update(comment));
    }

    @DeleteMapping(COMMENT_ID_MAPPING)
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ApiResponseDomain<Comment> deleteById(@NotNull @Positive @PathVariable Long commentId) {
        return ApiResponseDomain.of(commentService.deleteById(commentId));
    }
}
