package org.tfigo.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateCommentRequest (
        @JsonProperty("content") String content,
        @JsonProperty("post_id") Long postId,
        @JsonProperty("author_id") Long authorId) {
}
