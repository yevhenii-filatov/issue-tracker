package com.it.service.impl;

import com.it.exception.NotFoundException;
import com.it.mapper.PageMapper;
import com.it.mapper.PageableMapper;
import com.it.mapper.ProjectMapper;
import com.it.model.domain.Project;
import com.it.model.entity.ProjectEntity;
import com.it.model.entity.ProjectRegistrationEntity;
import com.it.model.entity.UserEntity;
import com.it.model.rest.Page;
import com.it.model.rest.query.ProjectQuery;
import com.it.repository.ProjectRegistrationRepository;
import com.it.repository.ProjectRepository;
import com.it.repository.UserRepository;
import com.it.service.ProjectService;
import com.it.specification.ProjectSpecification;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Yevhenii Filatov
 * @since 6/25/23
 */

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = DataAccessException.class)
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRegistrationRepository projectRegistrationRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserRepository userRepository;
    private final PageableMapper pageableMapper;
    private final PageMapper pageMapper;

    @Override
    public Project create(@NotNull Project project) {
        ProjectEntity mapped = projectMapper.toEntity(project);
        ProjectEntity saved = projectRepository.save(mapped);
        return projectMapper.fromEntity(saved);
    }

    @Override
    public Project deleteById(@NotNull Long projectId) {
        return findAndProcess(projectId, projectEntity -> {
            Project mapped = projectMapper.fromEntity(projectEntity);
            projectRepository.deleteById(projectId);
            return mapped;
        });
    }

    @Override
    public Page<Project> find(@NotNull ProjectQuery query) {
        Pageable pageable = pageableMapper.toPageable(query.getPageable());
        ProjectSpecification specification = new ProjectSpecification(query);
        return pageMapper
           .toPage(projectRepository.findAll(specification, pageable))
           .mapContent(projectMapper::fromEntity);
    }

    @Override
    public Project findById(@NotNull Long projectId) {
        return findAndProcess(projectId, projectMapper::fromEntity);
    }

    @Override
    public Project patchName(@NotNull Long projectId, String name) {
        return doPatch(projectId, projectEntity -> projectEntity.setName(name));
    }

    @Override
    public Project patchDescription(@NotNull Long projectId, String description) {
        return doPatch(projectId, projectEntity -> projectEntity.setDescription(description));
    }

    @Override
    public Project addUser(@NotNull Long projectId, @NotNull Long userId) {
        return doPatch(projectId, projectEntity -> userRepository
           .findById(userId)
           .map(userEntity -> projectEntity.getRegistrations().add(ProjectRegistrationEntity.create(projectEntity, userEntity)))
           .orElseThrow(() -> NotFoundException.ofId(userId, UserEntity.class))
        );
    }

    @Override
    public Project deleteUser(@NotNull Long projectId, @NotNull Long userId) {
        return doPatch(
           projectId, projectEntity -> projectRegistrationRepository.deleteByProjectIdAndUserId(projectId, userId)
        );
    }

    private Project doPatch(Long projectId, Consumer<ProjectEntity> patcher) {
        return findAndProcess(projectId, projectEntity -> {
            patcher.accept(projectEntity);
            ProjectEntity saved = projectRepository.save(projectEntity);
            return projectMapper.fromEntity(saved);
        });
    }

    private Project findAndProcess(Long projectId, Function<ProjectEntity, Project> processor) {
        return projectRepository.findById(projectId)
           .map(processor)
           .orElseThrow(() -> NotFoundException.ofId(projectId, ProjectEntity.class));
    }
}
