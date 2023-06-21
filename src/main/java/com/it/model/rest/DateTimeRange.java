package com.it.model.rest;

import com.it.model.domain.AbstractDomainObject;
import lombok.*;

/**
 * @author Yevhenii Filatov
 * @since 4/10/23
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DateTimeRange extends AbstractDomainObject {
    private Long from;
    private Long to;
}
