package com.it.mapper;

import com.it.model.domain.Comment;
import com.it.model.entity.CommentEntity;
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
public interface CommentMapper {
    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "taskId", source = "task.id")
    Comment fromEntity(CommentEntity entity);

    CommentEntity toEntity(Comment comment);
}
