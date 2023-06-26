package com.it.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Yevhenii Filatov
 * @since 6/14/23
 */

@Getter
@Setter
@Entity
@Table(name = "project_registration")
public class ProjectRegistrationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public static ProjectRegistrationEntity create(ProjectEntity projectEntity, UserEntity userEntity) {
        ProjectRegistrationEntity entity = new ProjectRegistrationEntity();
        entity.setProject(projectEntity);
        entity.setUser(userEntity);
        entity.setCreatedAt(LocalDateTime.now());
        return entity;
    }
}
