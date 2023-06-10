package com.it.mapper;

import com.it.model.domain.User;
import com.it.model.entity.UserEntity;
import com.it.util.Encryptor;
import org.mapstruct.*;

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
    @Mapping(target = "password", ignore = true)
    UserEntity toEntity(User user);

    @Mapping(target = "password", ignore = true)
    User fromEntity(UserEntity entity);

    @AfterMapping
    default void setPassword(@MappingTarget UserEntity entity, User user) {
        entity.setPassword(Encryptor.sha(user.getPassword()));
    }
}
