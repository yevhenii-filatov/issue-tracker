package com.it.repository;

import com.it.model.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Yevhenii Filatov
 * @since 6/26/23
 */

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
