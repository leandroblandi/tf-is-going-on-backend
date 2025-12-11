package org.tfigo.api.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tfigo.api.request.FollowOperationRequest;
import org.tfigo.api.response.ApiResponse;
import org.tfigo.api.response.UserSummaryResponse;
import org.tfigo.api.service.FollowService;

@RestController
@RequestMapping("/api/v1/follows")
public class FollowController {
    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> operate(@Valid @RequestBody FollowOperationRequest request) {
        followService.operate(request);
        return ResponseEntity.ok(ApiResponse.info("Follow operation performed successfully"));
    }

    @GetMapping("/{user_id}/followers")
    public ResponseEntity<ApiResponse<Page<UserSummaryResponse>>> findFollowers(
            @PathVariable("user_id") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        var response = ApiResponse.success(followService.findFollowers(userId, page, size));
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/{user_id}/followed")
    public ResponseEntity<ApiResponse<Page<UserSummaryResponse>>> findFollowing(
            @PathVariable("user_id") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        var response = ApiResponse.success(followService.findFollowing(userId, page, size));
        return ResponseEntity.status(200).body(response);
    }
}
