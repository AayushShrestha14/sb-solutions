package com.sb.solutions.api.companyInfo.entityInfo.entity;

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

import javax.persistence.*;
import java.util.Set;

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
    @JoinColumn(name = "swot_id", referencedColumnName = "id")
    private Swot swot;
    @OneToMany(mappedBy = "entityInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ManagementTeam> managementTeamList;
    @OneToMany(mappedBy = "entityInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Proprietor> proprietorsList;
}
