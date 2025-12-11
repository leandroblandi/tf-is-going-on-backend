package org.tfigo.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public record PostResponse (
        @JsonProperty("id") Long id,
        @JsonProperty("content") String content,
        @JsonProperty("author") UserSummaryResponse author,
        @JsonProperty("likes") List<String> userLikes,
        @JsonProperty("comments") List<CommentResponse> comments,
        @JsonProperty("published_at") LocalDateTime timestamp
) {
}
