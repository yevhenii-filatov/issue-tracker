package com.it.service.impl;

import com.it.exception.NotFoundException;
import com.it.mapper.UserMapper;
import com.it.model.domain.User;
import com.it.model.entity.UserEntity;
import com.it.repository.UserRepository;
import com.it.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Yevhenii Filatov
 * @since 6/10/23
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User register(@NotNull User user) {
        return save(user);
    }

    @Override
    public User findById(@NotNull Long userId) {
        return userRepository.findById(userId)
           .map(userMapper::fromEntity)
           .orElseThrow(() -> NotFoundException.ofId(userId, User.class));
    }

    @Override
    public User deleteById(@NotNull Long userId) {
        return userRepository.findById(userId)
           .map(userEntity -> {
               userRepository.deleteById(userId);
               return userMapper.fromEntity(userEntity);
           }).orElseThrow(() -> NotFoundException.ofId(userId, User.class));
    }

    @Override
    public User update(@NotNull Long userId, @NotNull User user) {
        return save(user);
    }

    private User save(User user) {
        UserEntity userEntity = userMapper.toEntity(user);
        return userMapper.fromEntity(userRepository.save(userEntity));
    }
}
