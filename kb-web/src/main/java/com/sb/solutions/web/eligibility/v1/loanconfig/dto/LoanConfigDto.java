package com.sb.solutions.web.eligibility.v1.loanconfig.dto;

import java.util.List;

import lombok.Data;

@Data
public class LoanConfigDto {

    private Long id;

    private String name;

    private List<DocumentDto> eligibilityDocuments;

    private Double minimumProposedAmount;

    private long totalPoints;

    private Double interestRate;
}
