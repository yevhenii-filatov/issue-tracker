package com.it.specification;

import com.it.model.rest.DateTimeRange;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author Yevhenii Filatov
 * @since 6/25/23
 */

public abstract class AbstractSpecification<T> implements Specification<T> {
    protected abstract @NotNull CriteriaBuilder getCriteriaBuilder();

    protected abstract @NotNull Root<T> getRoot();

    protected Predicate like(String fieldName, String text) {
        if (isNotBlank(fieldName) && nonNull(text)) {
            return getCriteriaBuilder().like(getRoot().get(fieldName), "%" + text + "%");
        }
        return null;
    }

    protected <D> Predicate in(String fieldName, Collection<D> values) {
        if (isNotBlank(fieldName) && CollectionUtils.isNotEmpty(values)) {
            return getRoot().get(fieldName).in(values);
        }
        return null;
    }

    protected Predicate dateBetween(String fieldName, DateTimeRange range) {
        if (isNotBlank(fieldName) && nonNull(range)) {
            return getCriteriaBuilder().between(getRoot().get(fieldName), range.getFrom(), range.getTo());
        }
        return null;
    }

    protected <D> Predicate equal(String fieldName, D value) {
        if (isNotBlank(fieldName) && nonNull(value)) {
            return getCriteriaBuilder().equal(getRoot().get(fieldName), value);
        }
        return null;
    }

    protected <D, V> Predicate equalJoined(String joiningTableName, String fieldName, V value) {
        if (isNotBlank(joiningTableName) && isNotBlank(fieldName) && nonNull(value)) {
            Join<D, ?> join = getRoot().join(joiningTableName);
            return getCriteriaBuilder().equal(join.get(fieldName), value);
        }
        return null;
    }
}
