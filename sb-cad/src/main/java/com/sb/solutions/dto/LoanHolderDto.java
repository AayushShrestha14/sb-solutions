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

    private Long id;
    private CustomerType customerType;
    private String name;
    private Branch branch;
    private Long associateId;
    private String idNumber;
    private Date idRegDate;
    private String idRegPlace;
    private List<CustomerLoanDto> customerLoanDtoList = new ArrayList<>();
    private Long totalLoan = 0L;
    private Long cadId;
}
