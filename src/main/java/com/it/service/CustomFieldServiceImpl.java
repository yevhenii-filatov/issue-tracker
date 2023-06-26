package com.it.service;

import com.it.exception.NotFoundException;
import com.it.mapper.CustomFieldMapper;
import com.it.model.domain.CustomField;
import com.it.model.entity.CustomFieldEntity;
import com.it.repository.CustomFieldRepository;
import com.it.repository.TaskRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Yevhenii Filatov
 * @since 6/26/23
 */

@Service
@RequiredArgsConstructor
public class CustomFieldServiceImpl implements CustomFieldService {
    private final TaskRepository taskRepository;
    private final CustomFieldRepository customFieldRepository;
    private final CustomFieldMapper customFieldMapper;

    @Override
    public CustomField create(@NotNull CustomField customField) {
        return doSave(customField);
    }

    @Override
    public CustomField update(@NotNull CustomField customField) {
        return doSave(customField);
    }

    @Override
    public CustomField deleteById(@NotNull Long customFieldId) {
        return customFieldRepository
           .findById(customFieldId)
           .map(customFieldEntity -> {
               CustomField mapped = customFieldMapper.fromEntity(customFieldEntity);
               customFieldRepository.deleteById(customFieldId);
               return mapped;
           })
           .orElseThrow(() -> NotFoundException.ofId(customFieldId, CustomFieldEntity.class));
    }

    private CustomField doSave(CustomField field) {
        return taskRepository
           .findById(field.getTaskId())
           .map(taskEntity -> {
               CustomFieldEntity mapped = customFieldMapper.toEntity(field);
               mapped.setTask(taskEntity);
               CustomFieldEntity saved = customFieldRepository.save(mapped);
               return customFieldMapper.fromEntity(saved);
           })
           .orElseThrow(() -> NotFoundException.ofId(field.getTaskId(), CustomFieldEntity.class));
    }
}
