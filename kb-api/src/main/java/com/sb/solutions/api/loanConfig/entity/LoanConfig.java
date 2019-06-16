package com.sb.solutions.api.loanConfig.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import com.sb.solutions.api.document.entity.Document;
import com.sb.solutions.api.loanTemplate.entity.LoanTemplate;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoanConfig extends BaseEntity<Long> {

    @NotNull(message = "name should not be null")
    private String name;
    @ManyToMany
    private List<LoanTemplate> templateList;
    private Boolean isFundable;
    private Status status;
    private Boolean isRenewable;
    @ManyToMany
    private List<Document> initial;
    @ManyToMany
    private List<Document> renew;
    @ManyToMany
    private List<Document> eligibilityDocuments;
    private Long totalPoints = 0L;
    private Boolean enableEligibility;
}
