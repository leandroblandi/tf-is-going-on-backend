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
import org.tfigo.api.repository.FollowRepository;
import org.tfigo.api.repository.UserRepository;
import org.tfigo.api.request.FollowOperationRequest;
import org.tfigo.api.request.enumerated.FollowOperationTypeEnum;
import org.tfigo.api.response.UserSummaryResponse;
import org.tfigo.api.service.FollowService;

import java.util.List;
import java.util.Optional;

@Service
public class FollowServiceImpl implements FollowService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final UserMapper userMapper;

    public FollowServiceImpl(UserRepository userRepository, FollowRepository followRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public void operate(FollowOperationRequest request) {
        Optional<UserEntity> followerOptional = userRepository.findById(request.followerId());
        Optional<UserEntity> followedOptional = userRepository.findById(request.followedId());

        if (followerOptional.isEmpty() || followedOptional.isEmpty()) {
            throw new ResourceNotFoundException("Follower or followed user with id %d, %d was not found".formatted(request.followerId(), request.followedId()));
        }

        UserEntity follower = followerOptional.get();
        UserEntity followed = followedOptional.get();
        handleOperation(follower, followed, request.operation());
    }

    private void handleOperation(UserEntity follower, UserEntity followed, FollowOperationTypeEnum operation) {
        if (operation == FollowOperationTypeEnum.FOLLOW) {
            follow(follower, followed);
        }

        if (operation == FollowOperationTypeEnum.UNFOLLOW) {
            unfollow(follower, followed);
        }
    }

    @Override
    public Page<UserSummaryResponse> findFollowers(Long userId, int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        return followRepository.findFollowers(userId, pageRequest)
                .map(userMapper::toSummary);

    }

    @Override
    public Page<UserSummaryResponse> findFollowing(Long userId, int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        return followRepository.findFollowing(userId, pageRequest)
                .map(userMapper::toSummary);
    }

    private void follow(UserEntity follower, UserEntity followed) {

        if (followed.getFollowers().contains(follower)) {
            throw new ResourceAlreadyExistsException("User %s is already following %s"
                    .formatted(follower.getUsername(), followed.getUsername()));
        }

        updateRelationsAndSave(FollowOperationTypeEnum.FOLLOW, follower, followed);
    }

    private void unfollow(UserEntity follower, UserEntity followed) {

        if (!followed.getFollowers().contains(follower)) {
            throw new ResourceNotFoundException("User %s is already not following %s"
                    .formatted(follower.getUsername(), followed.getUsername()));
        }

        updateRelationsAndSave(FollowOperationTypeEnum.UNFOLLOW, follower, followed);
    }

    private void updateRelationsAndSave(FollowOperationTypeEnum type, UserEntity follower, UserEntity followed) {

        if (type == FollowOperationTypeEnum.FOLLOW) {
            follower.addFollowing(followed);
            followed.addFollower(follower);
        }

        else {
            follower.removeFollowing(followed);
            followed.removeFollower(follower);
        }

        userRepository.saveAll(List.of(follower, followed));
    }
}
