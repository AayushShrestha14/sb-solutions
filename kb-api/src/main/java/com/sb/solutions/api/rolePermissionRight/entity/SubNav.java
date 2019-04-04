package com.sb.solutions.api.rolePermissionRight.entity;

import com.sb.solutions.core.enitity.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Rujan Maharjan on 3/28/2019
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubNav {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, nullable = false)
    private String subNavName;
    private String frontUrl;
    private String faIcon;

}
