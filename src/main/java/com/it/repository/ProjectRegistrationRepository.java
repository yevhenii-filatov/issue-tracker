package com.it.repository;

import com.it.model.entity.ProjectRegistrationEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Yevhenii Filatov
 * @since 6/25/23
 */

@Repository
public interface ProjectRegistrationRepository extends JpaRepository<ProjectRegistrationEntity, Long> {
    void deleteByProjectIdAndUserId(@NotNull @Positive Long projectId, @NotNull @Positive Long userId);
}
