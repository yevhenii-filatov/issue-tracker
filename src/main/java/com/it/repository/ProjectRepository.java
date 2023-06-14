package com.it.repository;

import com.it.model.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Yevhenii Filatov
 * @since 6/14/23
 */

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
}
