package com.sb.solutions.web.eligibility.v1.loanconfig.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class LoanConfigDTO {

    private Long id;

    private String name;

    private List<DocumentDTO> eligibilityDocuments;

    private long totalPoints;

    private double eligibilityPercentage;

}
