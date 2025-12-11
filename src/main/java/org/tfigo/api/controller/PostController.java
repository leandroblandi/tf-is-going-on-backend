package org.tfigo.api.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tfigo.api.request.CreatePostRequest;
import org.tfigo.api.request.ToggleLikeRequest;
import org.tfigo.api.response.ApiResponse;
import org.tfigo.api.response.PostResponse;
import org.tfigo.api.service.PostService;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{user_id}/feed")
    public ResponseEntity<ApiResponse<Page<PostResponse>>> findFeed(
            @PathVariable("user_id") Long userId,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
    ) {
        var response = ApiResponse.success(postService.findPostFromFollowedUsers(userId, page, size));
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createPost(@Valid @RequestBody CreatePostRequest request) {
        postService.createPost(request);
        return ResponseEntity.status(201).body(ApiResponse.info("Post created successfully"));
    }

    @PatchMapping("/{post_id}/like")
    public ResponseEntity<ApiResponse<String>> toggleLike(
            @PathVariable("post_id") Long postId,
            @RequestParam("user_id") Long userId
            ) {
        ToggleLikeRequest request = new ToggleLikeRequest(postId, userId);
        postService.toggleLike(request);
        var response = ApiResponse.<String>info("Post liked successfully");
        return ResponseEntity.status(201).body(response);
    }

    @DeleteMapping("/{post_id}")
    public ResponseEntity<ApiResponse<String>> deletePost(@PathVariable("post_id") Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok(ApiResponse.info("Post deleted successfully"));
    }
}
