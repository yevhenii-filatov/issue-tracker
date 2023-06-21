package com.it.model.rest;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Yevhenii Filatov
 * @since 4/7/23
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AbstractApiResponse {
}
