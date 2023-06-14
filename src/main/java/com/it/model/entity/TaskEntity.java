package com.it.model.entity;

import com.it.model.common.TaskPriority;
import com.it.model.common.TaskType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Yevhenii Filatov
 * @since 6/14/23
 */

@Getter
@Setter
@Entity
@Table(name = "task")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private UserEntity assignee;

    @ManyToOne
    @JoinColumn(name = "reporter_id", nullable = false)
    private UserEntity reporter;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskType type;

    @Column(name = "priority", nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @OneToMany(mappedBy = "task")
    private List<LabelEntity> labels = new ArrayList<>();

    @OneToMany(mappedBy = "task")
    private List<CommentEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy = "task")
    private List<CustomFieldEntity> customFields = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private TaskEntity parent;

    @OneToMany(mappedBy = "parent")
    private Set<TaskEntity> subTasks;
}
