package com.sb.solutions.api.loan.dto;

import lombok.Data;

import com.sb.solutions.core.enums.DocStatus;

/**
 * @author : Rujan Maharjan on  9/28/2020
 **/
@Data
public class CustomerLoanDto {

    private String loanHolderId;
    private String name;
    private String groupCode;
    private String groupLimit;
    private String groupId;
    private String proposedLimit;
    private DocStatus documentStatus;
    private String customerLoanId;

}
