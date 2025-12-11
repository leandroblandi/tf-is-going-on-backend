package org.tfigo.api.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tfigo.api.request.CreateUserRequest;
import org.tfigo.api.response.ApiResponse;
import org.tfigo.api.response.UserSummaryResponse;
import org.tfigo.api.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserSummaryResponse>>> findAll(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
    ) {
        var response = ApiResponse.success(userService.findAll(page, size));
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createUser(@Valid @RequestBody CreateUserRequest request) {
        userService.register(request);
        return ResponseEntity.status(201).body(ApiResponse.info("User created successfully"));
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<ApiResponse<String>> deactivateUser(@PathVariable("user_id") Long userId) {
        userService.deactivate(userId);
        return ResponseEntity.ok(ApiResponse.info("User deactivated successfully"));
    }

}
