package com.it.mapper;

import com.it.model.domain.Project;
import com.it.model.entity.ProjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * @author Yevhenii Filatov
 * @since 6/14/23
 */

@Mapper(
   componentModel = "spring",
   nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
   uses = {
      UserMapper.class,
      ProjectRegistrationMapper.class,
      TaskMapper.class
   }
)
public interface ProjectMapper {
    Project fromEntity(ProjectEntity entity);

    ProjectEntity toEntity(Project project);
}