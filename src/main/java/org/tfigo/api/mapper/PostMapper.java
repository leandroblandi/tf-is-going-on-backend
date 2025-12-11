package org.tfigo.api.mapper;

import org.springframework.stereotype.Component;
import org.tfigo.api.entity.PostEntity;
import org.tfigo.api.response.CommentResponse;
import org.tfigo.api.response.PostResponse;
import org.tfigo.api.response.UserSummaryResponse;

import java.util.List;

@Component
public class PostMapper {
    private final CommentMapper commentMapper;
    private final LikeMapper likeMapper;
    private final UserMapper userMapper;

    public PostMapper(CommentMapper commentMapper, LikeMapper likeMapper, UserMapper userMapper) {
        this.commentMapper = commentMapper;
        this.likeMapper = likeMapper;
        this.userMapper = userMapper;
    }

    public PostResponse toResponse(PostEntity postEntity) {
        UserSummaryResponse user = userMapper.toSummary(postEntity.getUser());
        List<CommentResponse> comments = commentMapper.toResponse(postEntity.getComments());
        List<String> userLikes = likeMapper.toUsernames(postEntity.getLikes());

        return new PostResponse(postEntity.getId(), postEntity.getContent(), user,
                userLikes, comments, postEntity.getCreatedAt());
    }
}
