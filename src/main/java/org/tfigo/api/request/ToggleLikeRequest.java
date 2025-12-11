package org.tfigo.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ToggleLikeRequest (
        @JsonProperty("post_id") Long postId,
        @JsonProperty("user_id") Long userId) {
}
