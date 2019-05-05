package com.sb.solutions.api.security.entity;

import com.sb.solutions.api.landSecurity.entity.LandSecurity;
import com.sb.solutions.api.valuator.entity.Valuator;
import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.Set;

//@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Security extends BaseEntity<Long> {
    private Date valuatedDate;
    private String valuatorRepresentativeName;
    private String staffRepresentativeName;
    @ManyToOne
    @JoinColumn(name="valuator_id")
    private Valuator valuator;
    @OneToMany

    private Set<LandSecurity> landSecurities;
}
