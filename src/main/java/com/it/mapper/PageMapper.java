package com.it.mapper;

import com.it.model.rest.Page;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * @author Yevhenii Filatov
 * @since 6/25/23
 */

@Mapper(
   componentModel = "spring",
   nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PageMapper {
    default <T> Page<T> toPage(org.springframework.data.domain.Page<T> page) {
        return new Page.PageBuilder<T>()
           .elements(page.getContent())
           .totalElements(page.getTotalElements())
           .pageSize(page.getPageable().getPageSize())
           .build();
    }
}
