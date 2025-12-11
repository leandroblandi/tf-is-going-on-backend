package org.tfigo.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.tfigo.api.entity.PostEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @Query("""
        select p
        from PostEntity p
        where p.user.id in (
            select f.id
            from UserEntity u
            join u.following f
            where u.id = :userId
        )
    """)
    Page<PostEntity> findPostsFromFollowedUsers(Long userId, Pageable pageable);

}
