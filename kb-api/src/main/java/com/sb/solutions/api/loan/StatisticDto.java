package com.sb.solutions.api.loan;

import com.sb.solutions.core.enums.DocStatus;

public interface StatisticDto {

    double getTotalAmount();

    DocStatus getStatus();

    String getloanType();
}
