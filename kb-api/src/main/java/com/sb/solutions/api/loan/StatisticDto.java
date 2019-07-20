package com.sb.solutions.api.loan;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.sb.solutions.core.enums.DocStatus;

@Data
@AllArgsConstructor
public class StatisticDto {

    double totalAmount;

    DocStatus status;

    String loanType;
}
