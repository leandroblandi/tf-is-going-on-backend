package org.tfigo.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.tfigo.api.entity.UserEntity;

@Repository
public interface FollowRepository extends JpaRepository<UserEntity, Long> {

    @Query("select f from UserEntity u join u.followers f where u.id = :userId")
    Page<UserEntity> findFollowers(Long userId, Pageable pageable);

    @Query("select f from UserEntity u join u.following f where u.id = :userId")
    Page<UserEntity> findFollowing(Long userId, Pageable pageable);
}
