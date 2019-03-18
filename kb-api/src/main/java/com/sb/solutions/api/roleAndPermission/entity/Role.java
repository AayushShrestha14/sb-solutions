package com.sb.solutions.api.roleAndPermission.entity;

import com.sb.solutions.core.enitity.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * Created by Rujan Maharjan on 3/18/2019.
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role extends AbstractBaseEntity<Long> {

    private String roleName;

    @ManyToMany
    @JoinTable(name = "role_permission_rights")
    private List<Permission> permission;
}
