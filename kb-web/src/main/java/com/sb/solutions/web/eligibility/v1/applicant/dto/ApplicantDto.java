package com.sb.solutions.web.eligibility.v1.applicant.dto;

import com.sb.solutions.api.eligibility.common.EligibilityStatus;
import com.sb.solutions.core.dto.BaseDto;
import com.sb.solutions.web.eligibility.v1.loanconfig.dto.LoanConfigDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApplicantDto extends BaseDto<Long> {

    private String fullName;

    private long age;

    private long phoneNumber;

    private double requestAmount;

    private String remarks;

    private EligibilityStatus eligibilityStatus;

    private long obtainedMarks;

    private LoanConfigDto loanConfig;

    private List<AnswerDto> answers = new ArrayList<>();

    private List<SubmissionDocumentDto> documents = new ArrayList<>();

    private BranchDto branch;

}
