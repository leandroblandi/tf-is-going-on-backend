package org.tfigo.api.mapper;

import org.springframework.stereotype.Component;
import org.tfigo.api.entity.LikeEntity;
import org.tfigo.api.entity.UserEntity;

import java.util.List;

@Component
public class LikeMapper {
    public List<String> toUsernames(List<LikeEntity> likeEntities) {
        return likeEntities.stream().map(LikeEntity::getUser)
                .map(UserEntity::getUsername).toList();
    }
}
