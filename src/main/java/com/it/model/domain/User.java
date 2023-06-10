package com.it.model.domain;

import com.it.model.common.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

/**
 * @author Yevhenii Filatov
 * @since 5/30/23
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractDomainObject {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private Set<Role> roles;
}
