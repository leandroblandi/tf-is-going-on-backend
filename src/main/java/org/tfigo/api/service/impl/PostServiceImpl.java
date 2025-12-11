package org.tfigo.api.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tfigo.api.entity.LikeEntity;
import org.tfigo.api.entity.PostEntity;
import org.tfigo.api.entity.UserEntity;
import org.tfigo.api.exception.ResourceNotFoundException;
import org.tfigo.api.mapper.PostMapper;
import org.tfigo.api.repository.LikeRepository;
import org.tfigo.api.repository.PostRepository;
import org.tfigo.api.repository.UserRepository;
import org.tfigo.api.request.CreatePostRequest;
import org.tfigo.api.request.ToggleLikeRequest;
import org.tfigo.api.response.PostResponse;
import org.tfigo.api.service.PostService;

import java.util.Optional;

@Slf4j
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final PostMapper postMapper;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, LikeRepository likeRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.postMapper = postMapper;
    }

    @Override
    @Transactional
    public void createPost(CreatePostRequest request) {
        Optional<UserEntity> userOptional = userRepository.findById(request.userId());

        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User with id %d was not found".formatted(request.userId()));
        }

        PostEntity entity = PostEntity.builder()
                .content(request.content())
                .user(userOptional.get())
                .build();
        postRepository.save(entity);
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public Page<PostResponse> findPostFromFollowedUsers(Long userId, int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        return postRepository
                .findPostsFromFollowedUsers(userId, pageRequest)
                .map(postMapper::toResponse);
    }

    @Override
    @Transactional
    public void toggleLike(ToggleLikeRequest request) {
        Optional<PostEntity> postOptional = postRepository.findById(request.postId());
        Optional<UserEntity> userOptional = userRepository.findById(request.userId());

        if (postOptional.isEmpty() || userOptional.isEmpty()) {
            throw new ResourceNotFoundException("Post or user with id %d, %d was not found".formatted(request.postId(),
                    request.userId()));
        }

        PostEntity postEntity = postOptional.get();
        Optional<LikeEntity> likeOptional = likeRepository.findByPostAndUser(postEntity, userOptional.get());

        if (likeOptional.isPresent()) {
            postEntity.removeLike(likeOptional.get());
            likeRepository.delete(likeOptional.get());
            return;
        }

        handleNewLike(postEntity, userOptional.get());
    }

    private void handleNewLike(PostEntity postEntity, UserEntity userEntity) {
        LikeEntity likeEntity = LikeEntity.builder()
                .user(userEntity)
                .post(postEntity)
                .build();

        postEntity.addLike(likeEntity);
        postRepository.save(postEntity);
        likeRepository.save(likeEntity);
    }
}
