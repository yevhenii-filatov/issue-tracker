package com.it.mapper;

import com.it.model.domain.Task;
import com.it.model.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
      LabelMapper.class,
      CommentMapper.class,
      CustomFieldMapper.class
   }
)
public interface TaskMapper {
    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "assigneeId", source = "assignee.id")
    @Mapping(target = "reporterId", source = "reporter.id")
    Task fromEntity(TaskEntity entity);

    TaskEntity toEntity(Task task);
}
