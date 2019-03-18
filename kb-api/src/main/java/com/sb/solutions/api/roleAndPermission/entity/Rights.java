package com.sb.solutions.api.roleAndPermission.entity;

import com.sb.solutions.core.enitity.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/**
 * Created by Rujan Maharjan on 3/18/2019.
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rights extends AbstractBaseEntity<Long> {

    private String rightName;




}
