package com.it.mapper;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Pageable;

/**
 * @author Yevhenii Filatov
 * @since 3/28/23
 */

@Mapper(componentModel = "spring", uses = PageableFactory.class)
public interface PageableMapper {
    Pageable toPageable(com.it.model.rest.Pageable pageable);
}
