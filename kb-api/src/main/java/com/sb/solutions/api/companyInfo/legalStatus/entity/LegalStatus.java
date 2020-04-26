package com.sb.solutions.api.companyInfo.legalStatus.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.sb.solutions.core.enitity.BaseEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@EntityListeners({AuditingEntityListener.class})
@Audited
public class LegalStatus extends BaseEntity<Long> {

    private String corporateStructure;

    private String registeredOffice;

    private String registeredUnderAct;

    private Date registrationDate;

    private String panRegistrationOffice;

    private Date panRegistrationDate;

    private Date registrationExpiryDate;



}
