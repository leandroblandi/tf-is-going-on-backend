package org.tfigo.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity @Table(name = "fucking_users")
@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "username", unique = true)
    private String username;

    @NotBlank
    @Column(name = "display_name")
    private String displayName;

    @Builder.Default
    @Column(name = "biography")
    private String biography = null;

    @Builder.Default
    @Column(name = "avatar_url")
    private String avatarUrl = null;

    @Column(name = "is_active")
    private boolean active;

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "user_following",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id")
    )
    private Set<UserEntity> followers = new HashSet<>();

    @ManyToMany
    private Set<UserEntity> following = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void addFollower(UserEntity user) {
        if(followers == null) {
            return;
        }
        followers.add(user);
    }

    public void addFollowing(UserEntity user) {
        if(following == null) {
            return;
        }
        following.add(user);
    }

    public void removeFollower(UserEntity user) {
        if(followers == null) {
            return;
        }
        followers.remove(user);
    }

    public void removeFollowing(UserEntity user) {
        if(following == null) {
            return;
        }
        following.remove(user);
    }
}
