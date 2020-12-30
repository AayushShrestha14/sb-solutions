package com.sb.solutions.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import com.sb.solutions.api.customer.entity.CustomerInfo;
import com.sb.solutions.api.loan.dto.LoanStageDto;
import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.enums.CADDocumentType;
import com.sb.solutions.enums.CadDocStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.sb.solutions.core.constant.AppConstant.DATE_FORMAT;

/**
 * @author : Rujan Maharjan on  12/1/2020
 **/

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerApprovedLoanCadDocumentation extends BaseEntity<Long> {

    @OneToOne
    private CustomerInfo loanHolder;

    @ManyToMany
    @JoinTable(name = "assigned_loan")
    private List<CustomerLoan> assignedLoan;

    @OneToOne(cascade = CascadeType.ALL)
    private CadStage cadCurrentStage;

    @OneToMany
    @JoinTable(name = "cad_file_customer_approved_loan_cad_documentation")
    private List<CadFile> cadFileList;

    private String cadStageList;

    private CadDocStatus docStatus;

    @Transient
    private List previousList;

    public List<LoanStageDto> getPreviousList() {
        if (this.getCadStageList() != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            try {
                this.previousList = objectMapper.readValue(this.getCadStageList(),
                    typeFactory.constructCollectionType(List.class, LoanStageDto.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            this.previousList = new ArrayList();
        }
        return this.previousList;
    }


}
