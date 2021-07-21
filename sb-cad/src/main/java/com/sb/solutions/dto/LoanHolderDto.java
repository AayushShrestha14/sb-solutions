package com.sb.solutions.dto;

import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.customer.enums.CustomerType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : Rujan Maharjan on  12/1/2020
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanHolderDto {

    private Long id = 0L;
    private CustomerType customerType;
    private String name;

    private Long associateId;
    private String idNumber;
    private Date idRegDate;
    private String idRegPlace;
    private String branchName;
    private String provinceName;
    private Long branchId;
    private List<CustomerLoanDto> customerLoanDtoList = new ArrayList<>();
    private Long totalLoan = 0L;
    private Long cadId;
    private Branch branch;

    public LoanHolderDto(Long id, CustomerType customerType, String name, Long associateId,
        String idNumber, Date idRegDate, String idRegPlace, String branchName,
        String provinceName,Long branchId) {
        this.id = id;
        this.customerType = customerType;
        this.name = name;
        this.associateId = associateId;
        this.idNumber = idNumber;
        this.idRegDate = idRegDate;
        this.idRegPlace = idRegPlace;
        this.branchName = branchName;
        this.provinceName = provinceName;
        this.branchId = branchId;
    }
}
