package org.tfigo.api.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tfigo.api.entity.CommentEntity;
import org.tfigo.api.entity.PostEntity;
import org.tfigo.api.entity.UserEntity;
import org.tfigo.api.exception.ResourceNotFoundException;
import org.tfigo.api.repository.CommentRepository;
import org.tfigo.api.repository.PostRepository;
import org.tfigo.api.repository.UserRepository;
import org.tfigo.api.request.CreateCommentRequest;
import org.tfigo.api.service.CommentService;

import java.util.Optional;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void createComment(CreateCommentRequest request) {
        Optional<PostEntity> postOptional = postRepository.findById(request.postId());
        Optional<UserEntity> userOptional = userRepository.findById(request.authorId());

        if (postOptional.isEmpty() || userOptional.isEmpty()) {
            throw new ResourceNotFoundException("Post or user with id %d, %d was not found".formatted(request.postId(),
                    request.authorId()));
        }

        CommentEntity commentEntity = CommentEntity.builder()
                .content(request.content())
                .user(userOptional.get())
                .build();

        PostEntity post = postOptional.get();
        addCommentToPostAndSave(commentEntity, post);
    }

    @Override
    public void deleteComment(Long commentId) {
        Optional<CommentEntity> commentOptional = commentRepository.findById(commentId);

        if (commentOptional.isEmpty()) {
            throw new ResourceNotFoundException("Comment with id %d was not found".formatted(commentId));
        }

        log.debug("Deleting comment with id: {}", commentId);
        commentRepository.deleteById(commentId);
    }

    private void addCommentToPostAndSave(CommentEntity commentEntity, PostEntity postEntity) {
        commentEntity.setPost(postEntity);
        commentRepository.save(commentEntity);
        postEntity.addComment(commentEntity);
        postRepository.save(postEntity);
    }
}
