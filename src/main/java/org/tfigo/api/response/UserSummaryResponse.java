package org.tfigo.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserSummaryResponse (
        @JsonProperty("username") String username,
        @JsonProperty("display_name") String displayName,
        @JsonProperty("avatar_url") String avatarUrl) {
}
