package org.tfigo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tfigo.api.entity.LikeEntity;
import org.tfigo.api.entity.PostEntity;
import org.tfigo.api.entity.UserEntity;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByPostAndUser(PostEntity post, UserEntity user);
}
