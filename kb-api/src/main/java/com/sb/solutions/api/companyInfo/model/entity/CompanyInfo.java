package com.sb.solutions.api.companyInfo.model.entity;

import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.util.Pair;

import com.sb.solutions.api.companyInfo.capital.entity.Capital;
import com.sb.solutions.api.companyInfo.companyLocations.entity.CompanyLocations;
import com.sb.solutions.api.companyInfo.legalStatus.entity.LegalStatus;
import com.sb.solutions.api.companyInfo.managementTeam.entity.ManagementTeam;
import com.sb.solutions.api.companyInfo.proprietor.entity.Proprietor;
import com.sb.solutions.api.companyInfo.swot.entity.Swot;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enitity.EntityValidator;
import com.sb.solutions.core.enums.BusinessType;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@EntityListeners({AuditingEntityListener.class})
@Audited
public class CompanyInfo extends BaseEntity<Long> implements EntityValidator {

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
    private String issuePlace;
    private String email;
    private String contactNum;
    private String additionalCompanyInfo;
    private String successionPlanning;

    @Transient
    private String bankingRelationship;
    private String industryGrowth;
    private String marketCompetition;
    private String experience;
    private String succession;
    private String businessAndIndustry;

    @OneToOne(cascade = CascadeType.ALL)
    private CompanyLocations companyLocations;

    private String contactPersons;

    @Override
    public Pair<Boolean, String> valid() {
        Boolean validator = Boolean.TRUE;
        StringBuilder validationMsg = new StringBuilder();
        if (StringUtils.isEmpty(this.companyName)
            || StringUtils.isEmpty(this.panNumber)
            || null == this.establishmentDate) {
            validator = Boolean.FALSE;
            validationMsg.append(
                "Company Name, PAN number, Establishment Date - any of these field cannot be left blank.");
        }
        // validate legal status field
        if (!this.legalStatus.isValid()) {
            validator = Boolean.FALSE;
            validationMsg.append(legalStatus.getValidationMsg());
        }

        // validate swot analysis field
        if (!this.swot.isValid()) {
            validator = Boolean.FALSE;
            validationMsg.append(swot.getValidationMsg());
        }

        // validate management team
        if (null == managementTeamList || managementTeamList.size() == 0) {
            validator = Boolean.FALSE;
            validationMsg
                .append("Company Info -At least one member required on Management Team Section.");
        } else {
            for (ManagementTeam managementTeam : managementTeamList) {
                if (!managementTeam.isValid()) {
                    validator = Boolean.FALSE;
                    validationMsg.append(managementTeam.getValidationMsg());
                    break;
                }
            }
        }

        // validate Proprietor Information
        if (null == proprietorsList || proprietorsList.size() == 0) {
            validator = Boolean.FALSE;
            validationMsg
                .append(
                    "Company Info -At least one member required on Proprietor Information Section.");
        } else {
            for (Proprietor proprietor : proprietorsList) {
                if (!proprietor.isValid()) {
                    validator = Boolean.FALSE;
                    validationMsg.append(proprietor.getValidationMsg());
                    break;
                }
            }
        }
        return Pair.of(validator, validationMsg.toString());
    }
}
