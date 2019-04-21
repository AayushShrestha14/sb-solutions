package com.sb.solutions.api.companyInfo.legalStatus.entity;

import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LegalStatus extends BaseEntity<Long> {

    private String companyName;

    private String corporateStructure;

    private String registeredOffice;

    private String registeredUnderAct;

    private String registrationNo;

    private Date registrationDate;

    private String panRegistrationOffice;

    private String panNumber;

    private Date panRegistrationDate;

}
