package org.tfigo.api.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tfigo.api.entity.UserEntity;
import org.tfigo.api.exception.ResourceAlreadyExistsException;
import org.tfigo.api.exception.ResourceNotFoundException;
import org.tfigo.api.mapper.UserMapper;
import org.tfigo.api.repository.UserRepository;
import org.tfigo.api.request.CreateUserRequest;
import org.tfigo.api.response.UserSummaryResponse;
import org.tfigo.api.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Page<UserSummaryResponse> findAll(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        return userRepository.findAll(pageRequest).map(userMapper::toSummary);
    }

    @Override
    @Transactional
    public void register(CreateUserRequest request) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(request.username());

        if (userOptional.isPresent()) {
            throw new ResourceAlreadyExistsException("User with username %s already exists".formatted(request.username()));
        }

        UserEntity entity = UserEntity.builder()
                .username(request.username())
                .displayName(request.displayName())
                .biography(request.biography())
                .avatarUrl(request.avatarUrl())
                .active(true)
                .build();
        userRepository.save(entity);
    }

    @Override
    @Transactional
    public void deactivate(Long userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User with id %d was not found".formatted(userId));
        }

        UserEntity userEntity = userOptional.get();
        userEntity.setActive(false);
        userRepository.save(userEntity);
    }
}
