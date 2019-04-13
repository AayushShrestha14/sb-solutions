package com.sb.solutions.api.user.entity;

import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.core.enitity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Sunil Babu Shrestha on 12/27/2018
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity<Long> {

    private String name;

    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private Status status;
    @OneToOne
    private Role role;
    private String accountNo;
    @OneToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
    private String signatureImage;
    private String profilePicture;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Set<FingerPrint> fingerPrint;



}
