package com.sb.solutions.api.loan.entity;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.sb.solutions.api.loan.OfferLetterStage;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.DocStatus;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerOfferLetter extends BaseEntity<Long> {

    private String path;

    private Boolean isOfferLetterIssued = false;

    private Boolean isOfferLetterApproved = false;

    private DocStatus  docStatus;

    private Long customerLoanId;

    @OneToOne(cascade = CascadeType.ALL)
    private OfferLetterStage offerLetterStage;

    private String offerLetterStageList;

    private String supportedInformation;

}
