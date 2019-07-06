package com.sb.solutions.api.companyInfo.entityInfo.entity;

import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.sb.solutions.api.companyInfo.capital.entity.Capital;
import com.sb.solutions.api.companyInfo.legalStatus.entity.LegalStatus;
import com.sb.solutions.api.companyInfo.managementTeam.entity.ManagementTeam;
import com.sb.solutions.api.companyInfo.proprietor.entity.Proprietor;
import com.sb.solutions.api.companyInfo.swot.entity.Swot;
import com.sb.solutions.core.enitity.BaseEntity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EntityInfo extends BaseEntity<Long> {

    @OneToOne(cascade = {
        CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private LegalStatus legalStatus;
    @OneToOne(cascade = {
            CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Capital capital;
    @OneToOne(cascade = {
        CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Swot swot;
    @OneToMany(fetch = FetchType.LAZY, cascade = {
        CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Set<ManagementTeam> managementTeamList;
    @OneToMany(fetch = FetchType.LAZY, cascade = {
        CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Set<Proprietor> proprietorsList;

    @PrePersist
    public void prePersist() {
        Date date = new Date();
        this.setLastModifiedAt(date);
        this.capital.setLastModifiedAt(date);
        this.legalStatus.setLastModifiedAt(date);
        this.swot.setLastModifiedAt(date);
        if (this.managementTeamList.size() <= 0) {
            this.setManagementTeamList(null);
        } else {
        }
        for (ManagementTeam managementTeam : this.managementTeamList){
            managementTeam.setLastModifiedAt(date);
        }
        if (this.proprietorsList.size() <= 0) {
            this.setProprietorsList(null);
        } else {
            for (Proprietor proprietor : this.proprietorsList) {
                proprietor.setLastModifiedAt(date);
            }
        }
    }
}
