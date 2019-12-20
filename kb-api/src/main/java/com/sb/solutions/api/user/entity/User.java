package com.sb.solutions.api.user.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Status;

/**
 * @author Sunil Babu Shrestha on 12/27/2018
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "users")
@EntityListeners({AuditingEntityListener.class})
@Audited
public class User extends BaseEntity<Long> implements UserDetails, Serializable {

    private String name;

    @Column(name = "user_name", unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @NotAudited
    @Column(nullable = false)
    private String password;

    @NotAudited
    private String resetPasswordToken;

    @NotAudited
    @Temporal(TemporalType.TIMESTAMP)
    private Date resetPasswordTokenExpiry;

    private Status status;

    @Audited
    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @NotAudited
    @ManyToMany
    private List<Branch> branch;

    private String signatureImage;

    @NotAudited
    private String profilePicture;

    @NotAudited
    private Boolean isDefaultCommittee = false;

    @Transient
    @JsonIgnore
    private List<String> authorityList;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String a : this.getAuthorityList()) {
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
