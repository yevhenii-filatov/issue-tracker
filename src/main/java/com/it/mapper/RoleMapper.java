package com.it.mapper;

import com.it.model.common.Role;
import com.it.model.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Yevhenii Filatov
 * @since 5/30/23
 */

@Mapper(
   componentModel = "spring",
   nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface RoleMapper {
    default Set<Role> fromEntities(Set<RoleEntity> roleEntities) {
        return roleEntities.stream()
           .map(RoleEntity::getName)
           .collect(Collectors.toCollection(HashSet::new));
    }

    default Set<RoleEntity> toEntities(Set<Role> roles) {
        return roles.stream()
           .map(RoleEntity::new)
           .collect(Collectors.toCollection(HashSet::new));
    }
}
