package com.it.mapper;

import com.it.model.domain.User;
import com.it.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * @author Yevhenii Filatov
 * @since 5/30/23
 */

@Mapper(
   componentModel = "spring",
   nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
   uses = RoleMapper.class
)
public interface UserMapper {
    UserEntity toEntity(User user);

    @Mapping(target = "password", ignore = true)
    User fromEntity(UserEntity entity);
}
