package com.shortlinkpro.url.entity;

import com.shortlinkpro.common.entity.BaseEntity;
import com.shortlinkpro.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "short_urls",
        indexes = {
                @Index(name = "idx_short_code", columnList = "shortCode"),
                @Index(name = "idx_custom_alias", columnList = "customAlias")
        }
)
public class ShortUrl extends BaseEntity {

    @Column(nullable = false, columnDefinition = "TEXT")
    private String originalUrl;

    @Column(nullable = false, unique = true, length = 20)
    private String shortCode;

    @Column(unique = true, length = 50)
    private String customAlias;

    @Column(length = 200)
    private String title;

    @Column(length = 500)
    private String description;

    private Instant expiresAt;

    @Builder.Default
    @Column(nullable = false)
    private Long clickCount = 0L;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

}
