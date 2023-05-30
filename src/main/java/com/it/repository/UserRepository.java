package com.it.repository;

import com.it.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Yevhenii Filatov
 * @since 5/30/23
 */

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
