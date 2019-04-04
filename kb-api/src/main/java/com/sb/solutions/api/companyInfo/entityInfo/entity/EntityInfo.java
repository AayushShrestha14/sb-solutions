package com.sb.solutions.api.companyInfo.entityInfo.entity;

import com.sb.solutions.api.companyInfo.capital.entity.Capital;
import com.sb.solutions.api.companyInfo.legalStatus.entity.LegalStatus;
import com.sb.solutions.api.companyInfo.managementTeam.entity.ManagementTeam;
import com.sb.solutions.api.companyInfo.proprietor.entity.Proprietor;
import com.sb.solutions.api.companyInfo.swot.entity.Swot;
import com.sb.solutions.core.enitity.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityInfo extends AbstractBaseEntity<Long> {
    @OneToOne(cascade = CascadeType.ALL)
    private LegalStatus legalStatus;
    @OneToOne(cascade = CascadeType.ALL)
    private Capital capital;
    @OneToOne(cascade = CascadeType.ALL)
    private Swot swot;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "entityInfo_id", referencedColumnName = "id")
    private Set<ManagementTeam> managementTeamList;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "entityInfo_id", referencedColumnName = "id")
    private Set<Proprietor> proprietorsList;
}
