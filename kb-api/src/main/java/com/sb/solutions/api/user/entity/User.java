package com.sb.solutions.api.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author Sunil Babu Shrestha on 12/27/2018
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity<Long> implements UserDetails,Serializable {

    private String name;

    @Column(name = "user_name", unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String resetPasswordToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date resetPasswordTokenExpiry;
    private Status status;

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;
    private String accountNo;

    @ManyToMany
    private List<Branch> branch;
    private String signatureImage;
    private String profilePicture;


    @Transient
    @JsonIgnore
    private List<String> authorityList;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
       for(String a: this.getAuthorityList()){
            authorities.add(new SimpleGrantedAuthority(a));
        }
        return authorities;
    }


    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
