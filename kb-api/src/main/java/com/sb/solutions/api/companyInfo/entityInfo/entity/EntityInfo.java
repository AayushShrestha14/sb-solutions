package com.sb.solutions.api.companyInfo.entityInfo.entity;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.sb.solutions.api.companyInfo.capital.entity.Capital;
import com.sb.solutions.api.companyInfo.legalStatus.entity.LegalStatus;
import com.sb.solutions.api.companyInfo.managementTeam.entity.ManagementTeam;
import com.sb.solutions.api.companyInfo.proprietor.entity.Proprietor;
import com.sb.solutions.api.companyInfo.swot.entity.Swot;
import com.sb.solutions.core.enitity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EntityInfo extends BaseEntity<Long> {

    @OneToOne(cascade = CascadeType.ALL)
    private LegalStatus legalStatus;
    @OneToOne(cascade = CascadeType.ALL)
    private Capital capital;
    @OneToOne(cascade = CascadeType.ALL)
    private Swot swot;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ManagementTeam> managementTeamList;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Proprietor> proprietorsList;
}
