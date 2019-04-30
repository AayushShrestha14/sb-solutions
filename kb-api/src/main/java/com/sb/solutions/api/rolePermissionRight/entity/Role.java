package com.sb.solutions.api.rolePermissionRight.entity;

import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 * Created by Rujan Maharjan on 3/25/2019.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity<Long> {

    @Transient
    private Long id;
    @Column(unique = true, nullable = false)
    private String roleName;

    private Status status = Status.ACTIVE;

}
