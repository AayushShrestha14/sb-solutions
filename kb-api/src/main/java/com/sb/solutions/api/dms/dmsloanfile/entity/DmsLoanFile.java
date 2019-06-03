package com.sb.solutions.api.dms.dmsloanfile.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sb.solutions.api.Loan.LoanStage;
import com.sb.solutions.api.loanConfig.entity.LoanConfig;
import com.sb.solutions.api.loanDocument.entity.LoanDocument;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.enums.LoanType;
import com.sb.solutions.core.enums.Priority;
import com.sb.solutions.core.enums.Securities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
public class DmsLoanFile extends BaseEntity<Long> {
    private String customerName;
    private String citizenshipNumber;
    private String contactNumber;
    private double interestRate;
    private double proposedAmount;
    private String security;
    @Column(columnDefinition = "text")
    private String documentPath;
    @OneToOne
    private LoanConfig loanConfig;
    @Transient
    private List<String> documentMap;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<LoanDocument> documents;
    @Transient
    private Set<Securities> securities;
    @Transient
    private List<Map<Object, Object>> documentPathMaps;
    private Date tenure;
    private Priority priority;
    private String recommendationConclusion;
    private String waiver;
    private DocStatus documentStatus;
    @OneToOne
    @JsonIgnore
    private LoanStage currentStage;
    @ElementCollection
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<LoanStage> previousStageList;
    private LoanType loanType;
}
