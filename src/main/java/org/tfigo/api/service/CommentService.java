package org.tfigo.api.service;

import org.tfigo.api.request.CreateCommentRequest;

public interface CommentService {
    void createComment(CreateCommentRequest request);

    void deleteComment(Long commentId);
}
