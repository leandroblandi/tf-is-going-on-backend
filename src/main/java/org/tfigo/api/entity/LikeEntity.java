package org.tfigo.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "fucking_post_likes")
@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
