package com.sb.solutions.api.Loan.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
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

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


    @Transient
    private List previousList;

    @Lob
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String previousStageList;

    public List getPreviousList() {
        if (this.getPreviousStageList() != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            try {
                this.previousList = objectMapper.readValue(this.getPreviousStageList(), typeFactory.constructCollectionType(List.class, Map.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            this.previousList = new ArrayList();
        }
        return this.previousList;
    }


}
