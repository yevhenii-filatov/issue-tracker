package com.it.service.impl;

import com.it.exception.NotFoundException;
import com.it.mapper.LabelMapper;
import com.it.model.domain.Label;
import com.it.model.entity.LabelEntity;
import com.it.model.entity.TaskEntity;
import com.it.repository.LabelRepository;
import com.it.repository.TaskRepository;
import com.it.service.LabelService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Yevhenii Filatov
 * @since 6/26/23
 */

@Service
@RequiredArgsConstructor
public class LabelServiceImpl implements LabelService {
    private final TaskRepository taskRepository;
    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;

    @Override
    public Label create(@NotNull Label label) {
        return doSave(label);
    }

    @Override
    public Label update(@NotNull Label label) {
        return doSave(label);
    }

    @Override
    public Label deleteById(@NotNull Long labelId) {
        return labelRepository
           .findById(labelId)
           .map(labelEntity -> {
               Label mapped = labelMapper.fromEntity(labelEntity);
               labelRepository.deleteById(labelId);
               return mapped;
           }).orElseThrow(() -> NotFoundException.ofId(labelId, LabelEntity.class));
    }

    private Label doSave(Label label) {
        return taskRepository
           .findById(label.getTaskId())
           .map(taskEntity -> {
               LabelEntity mapped = labelMapper.toEntity(label);
               mapped.setTask(taskEntity);
               LabelEntity saved = labelRepository.save(mapped);
               return labelMapper.fromEntity(saved);
           })
           .orElseThrow(() -> NotFoundException.ofId(label.getTaskId(), TaskEntity.class));
    }
}
