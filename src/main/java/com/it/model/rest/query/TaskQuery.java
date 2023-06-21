package com.it.model.rest.query;

import com.it.model.common.TaskPriority;
import com.it.model.common.TaskStatus;
import com.it.model.common.TaskType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

/**
 * @author Yevhenii Filatov
 * @since 6/21/23
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TaskQuery extends PageableQuery {
    private String pattern;
    private Long assigneeId;
    private Long reporterId;
    private Long projectId;
    private Long parentId;
    private Collection<TaskType> types;
    private Collection<TaskPriority> priorities;
    private Collection<TaskStatus> statuses;
}
