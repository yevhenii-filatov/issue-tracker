package com.it.model.domain;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Map;

/**
 * @author Yevhenii Filatov
 * @since 3/29/23
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TargetObject extends AbstractDomainObject {
    @NotNull
    private String targetId;

    private Map<String, String> properties;

    @Builder
    public TargetObject(@NotNull String targetId, Map<String, String> properties) {
        this.targetId = targetId;
        this.properties = properties;
    }

    public static TargetObject of(String targetId) {
        return TargetObject.builder().targetId(targetId).build();
    }
}
