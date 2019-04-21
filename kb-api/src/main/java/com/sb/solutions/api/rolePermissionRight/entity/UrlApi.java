package com.sb.solutions.api.rolePermissionRight.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Rujan Maharjan on 4/19/2019
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlApi {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String apiUrl;
    @NotNull
    private String type;
    @Transient
    private boolean checked;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "permission_api_list",
            joinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "api_list_id", referencedColumnName = "id")})
    private Permission permission;
}
