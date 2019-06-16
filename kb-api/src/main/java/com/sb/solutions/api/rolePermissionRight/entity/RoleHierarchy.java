package com.sb.solutions.api.rolePermissionRight.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rujan Maharjan on 5/13/2019
 */


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleHierarchy extends BaseEntity<Long> {

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;
    private Long roleOrder;

    @Transient
    private String roleName;

    @Transient
    private Long roleId;

    @Transient
    private Long userId;

    public RoleHierarchy(Long roleOrder, String roleName, Long roleId) {
        this.roleOrder = roleOrder;
        this.roleName = roleName;
        this.roleId = roleId;

    }
}
