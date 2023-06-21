package com.it.model.rest.query;

import com.it.model.rest.DateTimeRange;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Yevhenii Filatov
 * @since 6/21/23
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ProjectQuery extends PageableQuery{
    private String pattern;
    private DateTimeRange createdAt;
    private Long ownerId;
    private Long registeredUserId;
}
