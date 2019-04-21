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

    @OneToMany(mappedBy = "permission", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<SubNav> subNavs;

    @OneToMany(mappedBy = "permission", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<UrlApi> apiList;
}

