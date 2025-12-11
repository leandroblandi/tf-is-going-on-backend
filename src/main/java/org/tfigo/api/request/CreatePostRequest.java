package org.tfigo.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreatePostRequest(
        @JsonProperty("content") String content,
        @JsonProperty("author_id") Long userId) {
}
