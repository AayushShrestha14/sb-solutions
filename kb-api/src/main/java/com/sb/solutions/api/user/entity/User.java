package com.sb.solutions.api.user.entity;


import com.sb.solutions.core.enitity.AbstractBaseEntity;
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
public class User extends AbstractBaseEntity<Long> {
    private String name;
    @Column(unique = true, nullable = false)
    private String userName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private Status status;
    @ManyToMany
    private Set<Role> role;
    private String accountNo;
    private long associatedId;
    private String signatureImage;
    private String profilePicture;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Set<FingerPrint> fingerPrint;

}
