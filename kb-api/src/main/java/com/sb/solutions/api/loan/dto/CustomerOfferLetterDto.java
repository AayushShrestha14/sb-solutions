package com.sb.solutions.api.loan.dto;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.sb.solutions.api.loan.OfferLetterStage;
import com.sb.solutions.api.loan.entity.CustomerOfferLetterPath;
import com.sb.solutions.core.dto.BaseDto;
import com.sb.solutions.core.enums.DocStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOfferLetterDto  extends BaseDto<Long> {

    private Boolean isOfferLetterIssued = false;

    private Boolean isOfferLetterApproved = false;

    private DocStatus docStatus;

    @OneToOne(cascade = CascadeType.ALL)
    private OfferLetterStage offerLetterStage;

    private String offerLetterStageList;

    @Transient
    private List<CustomerOfferLetterPath> customerOfferLetterPath;

}
