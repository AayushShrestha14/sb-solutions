package com.sb.solutions.api.user.entity;


import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.core.enitity.AbstractBaseEntity;
import com.sb.solutions.core.enums.Status;
import com.sb.solutions.core.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * @author Sunil Babu Shrestha on 12/27/2018
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractBaseEntity<Long> {
    private String name;
    @Column(unique = true, nullable = false)
    private String userName;
    private String password;
    private Status status;
    private UserType userType;
    private long associateId;

    @OneToOne
    private Role role;

}
