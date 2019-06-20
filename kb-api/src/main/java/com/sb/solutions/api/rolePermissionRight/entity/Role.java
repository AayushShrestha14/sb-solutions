package com.sb.solutions.api.rolePermissionRight.entity;

import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.RoleAccess;
import com.sb.solutions.core.enums.RoleType;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Created by Rujan Maharjan on 3/25/2019.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity<Long> implements Serializable {

    @Transient
    private Long id;
    @Column(unique = true, nullable = false)
    private String roleName;

    private Status status = Status.ACTIVE;

    @Transient
    private String createdByName;

    @Transient
    private String modifiedByName;

    private RoleType roleType = RoleType.APPROVAL;

    private RoleAccess roleAccess;
}
