package com.sb.solutions.api.rolePermissionRight.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sb.solutions.core.enitity.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Rujan Maharjan on 3/25/2019.
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class RolePermissionRights extends AbstractBaseEntity<Long> {

    @Transient
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne
    private Role role;

    @OneToOne
    private Permission permission;

    @ManyToMany
    private List<Rights> rights;

    @Transient
    private boolean del;


}