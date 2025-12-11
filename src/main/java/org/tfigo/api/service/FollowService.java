package org.tfigo.api.service;

import org.springframework.data.domain.Page;
import org.tfigo.api.request.FollowOperationRequest;
import org.tfigo.api.response.UserSummaryResponse;

public interface FollowService {
    void operate(FollowOperationRequest request);

    Page<UserSummaryResponse> findFollowers(Long userId, int page, int size);

    Page<UserSummaryResponse> findFollowing(Long userId, int page, int size);
}
