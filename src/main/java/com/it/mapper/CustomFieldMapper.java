package com.it.mapper;

import com.it.model.domain.CustomField;
import com.it.model.entity.CustomFieldEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * @author Yevhenii Filatov
 * @since 6/14/23
 */

@Mapper(
   componentModel = "spring",
   nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CustomFieldMapper {
    @Mapping(target = "taskId", source = "task.id")
    CustomField fromEntity(CustomFieldEntity entity);

    CustomFieldEntity toEntity(CustomField customField);
}
