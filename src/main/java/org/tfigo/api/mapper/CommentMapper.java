package org.tfigo.api.mapper;

import org.springframework.stereotype.Component;
import org.tfigo.api.entity.CommentEntity;
import org.tfigo.api.response.CommentResponse;
import org.tfigo.api.response.UserSummaryResponse;

import java.util.List;

@Component
public class CommentMapper {
    private final UserMapper userMapper;

    public CommentMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public CommentResponse toResponse(CommentEntity commentEntity) {
        UserSummaryResponse user = userMapper.toSummary(commentEntity.getUser());
        return new CommentResponse(commentEntity.getId(), user, commentEntity.getContent(), commentEntity.getCreatedAt());
    }

    public List<CommentResponse> toResponse(List<CommentEntity> commentEntities) {
        return commentEntities.stream().map(this::toResponse).toList();
    }
}
