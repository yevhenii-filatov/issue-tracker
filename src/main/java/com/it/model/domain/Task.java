package com.it.model.domain;

import com.it.model.common.TaskPriority;
import com.it.model.common.TaskStatus;
import com.it.model.common.TaskType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

/**
 * @author Yevhenii Filatov
 * @since 6/14/23
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Task extends AbstractDomainObject {
    private String title;
    private String description;
    private User assignee;
    private User reporter;
    private TaskType taskType;
    private TaskPriority priority;
    private TaskStatus status;
    private Long projectId;
    private List<Label> labels;
    private List<Comment> comments;
    private List<CustomField> customFields;
    private Task parent;
    private Set<Task> subTasks;
}
