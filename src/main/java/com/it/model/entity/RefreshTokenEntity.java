package com.it.model.entity;

import com.it.util.Encryptor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Yevhenii Filatov
 * @since 3/28/23
 */

@Getter
@Setter
@Entity
@Table(name = "refresh_tokens")
public class RefreshTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "token")
    private String token;

    @NotNull
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public void setToken(String token) {
        this.token = Encryptor.md5Hex(token);
    }

    public static RefreshTokenEntity create(@NotBlank String token,
                                            @NotNull UserEntity user) {
        RefreshTokenEntity entity = new RefreshTokenEntity();
        entity.setToken(token);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUser(user);
        return entity;
    }
}
