package com.sb.solutions.api.roleAndPermission.entity;

import com.sb.solutions.core.enitity.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.List;

/**
 * Created by Rujan Maharjan on 3/18/2019.
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission extends AbstractBaseEntity<Long> {

    private String permissionName;

    @Transient
    private List<Rights> rightsList;

    @OneToMany
    private List<PermissionApi> permissionApis;


}
