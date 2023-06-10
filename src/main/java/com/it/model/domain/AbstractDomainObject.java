package com.it.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.beans.Transient;
import java.io.Serializable;

/**
 * @author Yevhenii Filatov
 * @since 5/30/23
 */

@Getter
@Setter
@ToString
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractDomainObject implements Serializable {
    @JsonProperty
    private Long id;

    @JsonProperty
    private String self;

    @Transient
    @JsonProperty
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
