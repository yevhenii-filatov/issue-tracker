package com.it.model.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Yevhenii Filatov
 * @since 6/14/23
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Label extends AbstractDomainObject {
    private String text;
    private Long taskId;
}
