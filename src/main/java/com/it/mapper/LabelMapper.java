package com.it.mapper;

import com.it.model.domain.Label;
import com.it.model.entity.LabelEntity;
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
public interface LabelMapper {
    @Mapping(target = "taskId", source = "task.id")
    Label fromEntity(LabelEntity entity);

    LabelEntity toEntity(Label label);
}
