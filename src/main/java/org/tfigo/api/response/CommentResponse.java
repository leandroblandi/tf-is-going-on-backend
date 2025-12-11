package org.tfigo.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record CommentResponse (
        @JsonProperty("id") Long id,
        @JsonProperty("author") UserSummaryResponse author,
        @JsonProperty("content") String content,
        @JsonProperty("published_at") LocalDateTime timestamp
) {
}
