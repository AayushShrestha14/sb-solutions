package com.sb.solutions.web.eligibility.v1.loanconfig.dto;

import lombok.Data;

import java.util.List;

@Data
public class LoanConfigDto {

    private Long id;

    private String name;

    private List<DocumentDto> eligibilityDocuments;

    private long totalPoints;
}
