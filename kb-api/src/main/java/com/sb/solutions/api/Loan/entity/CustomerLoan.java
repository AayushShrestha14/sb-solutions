package com.sb.solutions.api.Loan.entity;

import com.sb.solutions.api.Loan.LoanStage;
import com.sb.solutions.api.basicInfo.customer.entity.Customer;
import com.sb.solutions.api.companyInfo.entityInfo.entity.EntityInfo;
import com.sb.solutions.api.dms.dmsloanfile.entity.DmsLoanFile;
import com.sb.solutions.api.loanConfig.entity.LoanConfig;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.enums.LoanType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * @author Rujan Maharjan on 6/4/2019
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoan extends BaseEntity<Long> {

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Customer customerInfo;

    @OneToOne
    private LoanConfig loan;

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private EntityInfo entityInfo;

    private LoanType loanType;

    private DocStatus documentStatus = DocStatus.PENDING;


    @ManyToOne
    private DmsLoanFile dmsLoanFile;

    @OneToOne(cascade = CascadeType.ALL)
    private LoanStage currentStage;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<LoanStage> previousStageList;


}
