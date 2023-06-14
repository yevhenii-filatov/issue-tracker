package com.it.mapper;

import com.it.model.domain.ProjectRegistration;
import com.it.model.entity.ProjectRegistrationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * @author Yevhenii Filatov
 * @since 6/14/23
 */

@Mapper(
   componentModel = "spring",
   nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
   uses = UserMapper.class
)
public interface ProjectRegistrationMapper {
    ProjectRegistration fromEntity(ProjectRegistrationEntity entity);

    ProjectRegistrationEntity toEntity(ProjectRegistration projectRegistration);
}
