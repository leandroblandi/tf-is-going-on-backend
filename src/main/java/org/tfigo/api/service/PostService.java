package org.tfigo.api.service;

import org.springframework.data.domain.Page;
import org.tfigo.api.request.CreatePostRequest;
import org.tfigo.api.request.ToggleLikeRequest;
import org.tfigo.api.response.PostResponse;

public interface PostService {
    void createPost(CreatePostRequest request);

    void deletePost(Long postId);

    Page<PostResponse> findPostFromFollowedUsers(Long userId, int page, int size);

    void toggleLike(ToggleLikeRequest request);
}
