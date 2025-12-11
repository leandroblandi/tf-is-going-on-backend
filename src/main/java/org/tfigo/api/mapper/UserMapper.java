package org.tfigo.api.mapper;

import org.springframework.stereotype.Component;
import org.tfigo.api.entity.UserEntity;
import org.tfigo.api.response.UserSummaryResponse;

@Component
public class UserMapper {

    public UserSummaryResponse toSummary(UserEntity userEntity) {
        return new UserSummaryResponse(userEntity.getUsername(), userEntity.getDisplayName(),
                userEntity.getAvatarUrl());
    }

}
