package org.tfigo.api.service;

import org.springframework.data.domain.Page;
import org.tfigo.api.request.CreateUserRequest;
import org.tfigo.api.response.UserSummaryResponse;

public interface UserService {
    Page<UserSummaryResponse> findAll(int page, int size);

    void register(CreateUserRequest request);

    void deactivate(Long userId);
}
