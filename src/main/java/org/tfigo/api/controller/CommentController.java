package org.tfigo.api.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tfigo.api.request.CreateCommentRequest;
import org.tfigo.api.response.ApiResponse;
import org.tfigo.api.service.CommentService;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createComment(@Valid @RequestBody CreateCommentRequest request) {
        commentService.createComment(request);
        return ResponseEntity.ok(ApiResponse.info("Comment created successfully"));
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<ApiResponse<String>> deleteComment(@PathVariable("comment_id") Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok(ApiResponse.info("Comment deleted successfully"));
    }
}
