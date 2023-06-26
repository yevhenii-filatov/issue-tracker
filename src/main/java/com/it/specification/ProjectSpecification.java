package com.it.specification;

import com.google.common.collect.Lists;
import com.it.model.entity.ProjectEntity;
import com.it.model.rest.query.ProjectQuery;
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
public class ProjectSpecification extends AbstractSpecification<ProjectEntity> {
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String CREATED_AT = "createdAt";
    private static final String OWNER_ID = "owner_id";
    private static final String USER_ID = "user_id";
    private static final String USERS_TABLE = "users";
    private static final String PROJECT_REGISTRATIONS_TABLE = "project_registration";

    private final ProjectQuery projectQuery;
    private Root<ProjectEntity> root;
    private CriteriaBuilder criteriaBuilder;

    @Override
    public Predicate toPredicate(@NonNull Root<ProjectEntity> root,
                                 @NonNull CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
        this.root = root;
        this.criteriaBuilder = criteriaBuilder;
        Predicate[] conditions = Lists.newArrayList(
              like(NAME, projectQuery.getPattern()),
              like(DESCRIPTION, projectQuery.getPattern()),
              dateBetween(CREATED_AT, projectQuery.getCreatedAt()),
              equal(OWNER_ID, projectQuery.getOwnerId()),
              equalJoined(PROJECT_REGISTRATIONS_TABLE, USER_ID, projectQuery.getRegisteredUserId())
           )
           .stream()
           .filter(Objects::nonNull)
           .toArray(Predicate[]::new);
        return criteriaBuilder.or(conditions);
    }
}
