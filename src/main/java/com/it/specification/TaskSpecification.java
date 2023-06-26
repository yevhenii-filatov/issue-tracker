package com.it.specification;

import com.google.common.collect.Lists;
import com.it.model.entity.TaskEntity;
import com.it.model.rest.query.TaskQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * @author Yevhenii Filatov
 * @since 6/25/23
 */

@Getter
@RequiredArgsConstructor
public class TaskSpecification extends AbstractSpecification<TaskEntity> {
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String TYPE = "task_type";
    private static final String PRIORITY = "priority";
    private static final String STATUS = "status";
    private static final String ASSIGNEE_ID = "assignee_id";
    private static final String REPORTER_ID = "assignee_id";
    private static final String PROJECT_ID = "project_id";
    private static final String PARENT_ID = "parent_id";

    private final TaskQuery taskQuery;
    private Root<TaskEntity> root;
    private CriteriaBuilder criteriaBuilder;

    @Override
    public Predicate toPredicate(@NonNull Root<TaskEntity> root,
                                 @NonNull CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
        this.root = root;
        this.criteriaBuilder = criteriaBuilder;
        Predicate[] conditions = Lists.newArrayList(
              like(TITLE, taskQuery.getPattern()),
              like(DESCRIPTION, taskQuery.getPattern()),
              equal(ASSIGNEE_ID, taskQuery.getAssigneeId()),
              equal(REPORTER_ID, taskQuery.getReporterId()),
              equal(PROJECT_ID, taskQuery.getProjectId()),
              equal(PARENT_ID, taskQuery.getParentId()),
              in(STATUS, taskQuery.getTypes()),
              in(PRIORITY, taskQuery.getPriorities()),
              in(STATUS, taskQuery.getStatuses())
           )
           .stream()
           .filter(Objects::nonNull)
           .toArray(Predicate[]::new);
        return criteriaBuilder.or(conditions);
    }
}
