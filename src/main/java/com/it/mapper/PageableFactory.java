package com.it.mapper;

import org.mapstruct.ObjectFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.fromString;
import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * @author Yevhenii Filatov
 * @since 3/28/23
 */

@Component
public class PageableFactory {
    @ObjectFactory
    Pageable create(com.it.model.rest.Pageable pageable) {
        Collection<com.it.model.rest.Sort> sorts = pageable.getPrefixedSorts();
        Optional<Sort> sortClauseOpt = isEmpty(sorts)
           ? Optional.empty()
           : sorts
           .stream()
           .map(sort -> {
               Sort.Direction direction = fromString(sort.getSortOrder());
               return Sort.by(direction, sort.getFieldName());
           })
           .reduce(Sort::and);

        return sortClauseOpt
           .map(sortClause -> PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortClause))
           .orElse(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
    }
}
