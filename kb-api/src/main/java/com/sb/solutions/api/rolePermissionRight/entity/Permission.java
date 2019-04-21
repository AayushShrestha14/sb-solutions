package com.sb.solutions.api.rolePermissionRight.entity;

import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, nullable = false)
    private String permissionName;
    private String frontUrl;
    private String faIcon;
    private Long orders;
    private Status status = Status.ACTIVE;

    @OneToMany
    private List<SubNav> subNavs;

    @OneToMany
    private List<UrlApi> apiList;
}

