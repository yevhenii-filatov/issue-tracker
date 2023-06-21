package com.it.service;

import com.it.model.domain.Comment;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * @author Yevhenii Filatov
 * @since 6/21/23
 */

public interface CommentService {
    Comment create(@NotNull @Valid Comment comment);

    Comment update(@NotNull @Valid Comment comment);

    Comment deleteById(@NotNull @Positive Long commentId);
}
