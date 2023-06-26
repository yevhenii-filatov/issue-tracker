package com.it.service.impl;

import com.it.exception.NotFoundException;
import com.it.mapper.PageMapper;
import com.it.mapper.PageableMapper;
import com.it.mapper.TaskMapper;
import com.it.model.common.TaskPriority;
import com.it.model.common.TaskStatus;
import com.it.model.common.TaskType;
import com.it.model.domain.Task;
import com.it.model.entity.TaskEntity;
import com.it.model.entity.UserEntity;
import com.it.model.rest.Page;
import com.it.model.rest.query.TaskQuery;
import com.it.repository.TaskRepository;
import com.it.repository.UserRepository;
import com.it.service.TaskService;
import com.it.specification.TaskSpecification;
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
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final PageableMapper pageableMapper;
    private final PageMapper pageMapper;
    private final UserRepository userRepository;

    @Override
    public Task create(@NotNull Task task) {
        TaskEntity mapped = taskMapper.toEntity(task);
        TaskEntity saved = taskRepository.save(mapped);
        return taskMapper.fromEntity(saved);
    }

    @Override
    public Page<Task> find(@NotNull TaskQuery query) {
        Pageable pageable = pageableMapper.toPageable(query.getPageable());
        TaskSpecification specification = new TaskSpecification(query);
        return pageMapper
           .toPage(taskRepository.findAll(specification, pageable))
           .mapContent(taskMapper::fromEntity);
    }

    @Override
    public Task findById(@NotNull Long taskId) {
        return findAndProcess(taskId, taskMapper::fromEntity);
    }

    @Override
    public Task deleteById(@NotNull Long taskId) {
        return findAndProcess(taskId, taskEntity -> {
            Task mapped = taskMapper.fromEntity(taskEntity);
            taskRepository.deleteById(taskId);
            return mapped;
        });
    }

    @Override
    public Task patchTitle(@NotNull Long taskId, String title) {
        return doPatch(taskId, taskEntity -> taskEntity.setTitle(title));
    }

    @Override
    public Task patchDescription(@NotNull Long taskId, String description) {
        return doPatch(taskId, taskEntity -> taskEntity.setDescription(description));
    }

    @Override
    public Task patchStatus(@NotNull Long taskId, @NotNull TaskStatus status) {
        return doPatch(taskId, taskEntity -> taskEntity.setStatus(status));
    }

    @Override
    public Task patchPriority(@NotNull Long taskId, @NotNull TaskPriority priority) {
        return doPatch(taskId, taskEntity -> taskEntity.setPriority(priority));
    }

    @Override
    public Task patchType(@NotNull Long taskId, @NotNull TaskType type) {
        return doPatch(taskId, taskEntity -> taskEntity.setTaskType(type));
    }

    @Override
    public Task assignToUser(@NotNull Long taskId, @NotNull Long userId) {
        return doPatch(taskId, taskEntity -> userRepository
           .findById(userId)
           .map(userEntity -> {
               taskEntity.setAssignee(userEntity);
               return userEntity;
           })
           .orElseThrow(() -> NotFoundException.ofId(userId, UserEntity.class))
        );
    }

    private Task doPatch(Long taskId, Consumer<TaskEntity> patcher) {
        return findAndProcess(taskId, taskEntity -> {
            patcher.accept(taskEntity);
            TaskEntity saved = taskRepository.save(taskEntity);
            return taskMapper.fromEntity(saved);
        });
    }

    private Task findAndProcess(Long taskId, Function<TaskEntity, Task> processor) {
        return taskRepository.findById(taskId)
           .map(processor)
           .orElseThrow(() -> NotFoundException.ofId(taskId, TaskEntity.class));
    }
}
