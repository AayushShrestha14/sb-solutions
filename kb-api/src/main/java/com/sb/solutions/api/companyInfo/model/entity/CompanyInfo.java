package com.sb.solutions.api.companyInfo.model.entity;

import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.sb.solutions.api.companyInfo.capital.entity.Capital;
import com.sb.solutions.api.companyInfo.legalStatus.entity.LegalStatus;
import com.sb.solutions.api.companyInfo.managementTeam.entity.ManagementTeam;
import com.sb.solutions.api.companyInfo.proprietor.entity.Proprietor;
import com.sb.solutions.api.companyInfo.swot.entity.Swot;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.BusinessType;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@EntityListeners({AuditingEntityListener.class})
@Audited
public class CompanyInfo extends BaseEntity<Long> {

    @OneToOne(cascade = CascadeType.ALL)
    private LegalStatus legalStatus;

    @OneToOne(cascade = CascadeType.ALL)
    private Capital capital;

    @OneToOne(cascade = CascadeType.ALL)
    private Swot swot;

    @AuditJoinTable
    @OneToMany(cascade = CascadeType.ALL)
    private Set<ManagementTeam> managementTeamList;

    @AuditJoinTable
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Proprietor> proprietorsList;

    private String companyName;
    private String registrationNumber;
    private Date establishmentDate;
    private BusinessType businessType;
    private String panNumber;
}
