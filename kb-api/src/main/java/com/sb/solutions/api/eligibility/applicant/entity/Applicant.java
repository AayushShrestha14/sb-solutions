package com.sb.solutions.api.eligibility.applicant.entity;

import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.eligibility.answer.entity.Answer;
import com.sb.solutions.api.eligibility.common.EligibilityStatus;
import com.sb.solutions.api.eligibility.document.entity.SubmissionDocument;
import com.sb.solutions.api.loanConfig.entity.LoanConfig;
import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Applicant extends BaseEntity<Long> {

    private String firstName;

    private String lastName;

    private long age;

    private long phoneNumber;

    private String nationality;

    private double requestAmount;

    private String remarks;

    private EligibilityStatus eligibilityStatus;

    @ManyToOne
    @JoinColumn(name = "loan_config_id")
    private LoanConfig loanConfig;

    @ManyToMany
    @JoinTable(name = "applicant_answer", joinColumns = @JoinColumn(name = "applicant_id"), inverseJoinColumns =
    @JoinColumn(name = "answer_id"))
    private List<Answer> answers = new ArrayList<>();

    @OneToMany
    @JoinTable(name = "applicant_document", joinColumns = @JoinColumn(name = "applicant_id"), inverseJoinColumns =
    @JoinColumn(name = "submission_document_id"))
    private List<SubmissionDocument> documents;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
}
