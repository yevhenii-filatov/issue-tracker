package com.it.service.impl;

import com.it.exception.NotFoundException;
import com.it.mapper.CommentMapper;
import com.it.model.domain.Comment;
import com.it.model.entity.CommentEntity;
import com.it.model.entity.TaskEntity;
import com.it.model.entity.UserEntity;
import com.it.repository.CommentRepository;
import com.it.repository.TaskRepository;
import com.it.repository.UserRepository;
import com.it.service.CommentService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Yevhenii Filatov
 * @since 6/26/23
 */

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public Comment create(@NotNull Comment comment) {
        return doSave(comment);
    }

    @Override
    public Comment update(@NotNull Comment comment) {
        return doSave(comment);
    }

    @Override
    public Comment deleteById(@NotNull Long commentId) {
        return commentRepository
           .findById(commentId)
           .map(commentEntity -> {
               Comment mapped = commentMapper.fromEntity(commentEntity);
               commentRepository.deleteById(commentId);
               return mapped;
           })
           .orElseThrow(() -> NotFoundException.ofId(commentId, CommentEntity.class));
    }

    private Comment doSave(Comment comment) {
        return taskRepository
           .findById(comment.getTaskId())
           .map(taskEntity -> userRepository
              .findById(comment.getAuthorId())
              .map(authorUserEntity -> {
                  CommentEntity mapped = commentMapper.toEntity(comment);
                  mapped.setTask(taskEntity);
                  mapped.setAuthor(authorUserEntity);
                  CommentEntity saved = commentRepository.save(mapped);
                  return commentMapper.fromEntity(saved);
              })
              .orElseThrow(() -> NotFoundException.ofId(comment.getAuthorId(), UserEntity.class))
           ).orElseThrow(() -> NotFoundException.ofId(comment.getTaskId(), TaskEntity.class));
    }
}
