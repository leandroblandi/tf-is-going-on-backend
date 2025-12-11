package org.tfigo.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "fucking_posts")
@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 10, max = 280)
    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL)
    private List<LikeEntity> likes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<CommentEntity> comments = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void addComment(CommentEntity comment) {
        if (comments == null) {
            return;
        }
        comments.add(comment);
    }

    public void addLike(LikeEntity like) {
        if (likes == null) {
            return;
        }
        likes.add(like);
    }

    public void removeLike(LikeEntity like) {
        if (likes == null) {
            return;
        }

        likes.remove(like);
    }
}
