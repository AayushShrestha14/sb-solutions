package com.sb.solutions.api.rolePermissionRight.entity;

import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by Rujan Maharjan on 3/25/2019.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity<Long> {

    @Transient
    private Long id;
    @Column(name="role_name", unique = true, nullable = false)
    private String roleName;

    @Column(name = "status")
    private Status status = Status.ACTIVE;

    @OneToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;
    @OneToOne
    @JoinColumn(name = "last_modified_by_id")
    private User lastModifiedBy;
}
