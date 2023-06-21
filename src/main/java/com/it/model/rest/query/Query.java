package com.it.model.rest.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Yevhenii Filatov
 * @since 3/28/23
 */

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Query implements Serializable {
}

