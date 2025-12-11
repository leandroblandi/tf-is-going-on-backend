package org.tfigo.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateUserRequest(
        @JsonProperty("username") String username,
        @JsonProperty("display_name") String displayName,
        @JsonProperty("biography") String biography,
        @JsonProperty("avatar_url") String avatarUrl) {
}
