package com.sb.solutions.api.user.entity;


import javax.persistence.Column;
import javax.persistence.Entity;

import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Status;
import com.sb.solutions.core.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sunil Babu Shrestha on 12/27/2018
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity<Long> {

    private String name;

    @Column(name = "username", unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    private Status status;

    @Column(name = "user_type")
    private UserType userType;

    @Column(name = "associated_id")
    private long associateId = 0;

    @Override
    protected void setId(Long id) {
        super.setId(id);
    }
}
