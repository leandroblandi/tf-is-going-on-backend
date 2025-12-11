package org.tfigo.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.tfigo.api.request.enumerated.FollowOperationTypeEnum;

public record FollowOperationRequest(
        @JsonProperty("follower_id") Long followerId,
        @JsonProperty("followed_id") Long followedId,
        @JsonProperty("follow_operation_type") FollowOperationTypeEnum operation
) {
}
