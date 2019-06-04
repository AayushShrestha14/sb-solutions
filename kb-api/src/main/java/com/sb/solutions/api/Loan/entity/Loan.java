package com.sb.solutions.api.Loan.entity;

import com.sb.solutions.api.Loan.LoanStage;
import com.sb.solutions.api.basicInfo.customer.entity.Customer;
import com.sb.solutions.api.companyInfo.entityInfo.entity.EntityInfo;
import com.sb.solutions.api.loanConfig.entity.LoanConfig;
import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * @author Rujan Maharjan on 6/4/2019
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loan extends BaseEntity<Long> {

    @OneToOne
    private Customer customerInfo;

    @OneToOne
    private LoanConfig loanType;

    @OneToOne
    private EntityInfo companyInfo;

    @OneToOne
    private LoanStage stage;


}
